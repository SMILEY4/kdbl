package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.utils.getFirst
import de.ruegnerlukas.kdbl.utils.map
import java.sql.ResultSet

class StatementReturningResult(private val resultSet: ResultSet) {

	/**
	 * @return the first row mapped to the given value or throw an [NoSuchElementException] if no row is available
	 */
	fun <T> getOne(mapper: (row: ResultSet) -> T): T {
		val result = resultSet.getFirst()
		if (result == null) {
			throw NoSuchElementException()
		} else {
			return mapper(result)
		}
	}


	/**
	 * @return the first row mapped to the given value or null if no row is available
	 */
	fun <T> getOneOrNone(mapper: (row: ResultSet) -> T): T? {
		val result = resultSet.getFirst()
		if (result == null) {
			return null
		} else {
			return mapper(result)
		}
	}


	/**
	 * @return the rows mapped to the given values or throw an [NoSuchElementException] if no row is available
	 */
	fun <T> getMultiple(mapper: (row: ResultSet) -> T): List<T> {
		val results = resultSet.map { mapper(it) }
		if (results.isEmpty()) {
			throw NoSuchElementException()
		} else {
			return results
		}
	}


	/**
	 * @return the rows mapped to the given values or an empty list if no row is available
	 */
	fun <T> getMultipleOrNone(mapper: (row: ResultSet) -> T): List<T> {
		return resultSet.map { mapper(it) }
	}


	/**
	 * @return the amount of returned rows
	 */
	fun getRowCount(): Int {
		return resultSet.map { 1 }.fold(0) { a, b -> a + b }
	}


	/**
	 * @return whether the amount of returned rows is exactly one
	 */
	fun isOne(): Boolean {
		return getRowCount() == 1
	}


	/**
	 * @return whether the amount of returned rows is exactly zero
	 */
	fun isNone(): Boolean {
		return getRowCount() == 0
	}


	/**
	 * @return whether the amount of returned rows is more than one
	 */
	fun isMultiple(): Boolean {
		return getRowCount() > 1
	}


	/**
	 * throw a [NoSuchElementException] if the amount of resulting rows is zero
	 */
	fun checkMultiple() {
		if (!isMultiple()) {
			throw NoSuchElementException()
		}
	}


	/**
	 * throw a [NoSuchElementException] if the amount of returned rows is less or more than one
	 */
	fun checkOne() {
		if (!isMultiple()) {
			throw NoSuchElementException()
		}
	}

}