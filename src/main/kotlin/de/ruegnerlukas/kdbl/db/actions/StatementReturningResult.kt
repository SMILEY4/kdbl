package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.dsl.expression.Column


class StatementReturningResult(private val results: List<Map<String, Any?>>) {

	/**
	 * @return the first row mapped to the given value or throw an [NoSuchElementException] if no row is available
	 */
	fun <T> getOne(mapper: (row: RowProvider) -> T): T {
		val result = results.getOrNull(0)
		if (result == null) {
			throw NoSuchElementException()
		} else {
			return mapper(RowProvider(result))
		}
	}


	/**
	 * @return the first row mapped to the given value or null if no row is available
	 */
	fun <T> getOneOrNone(mapper: (row: RowProvider) -> T): T? {
		val result = results.getOrNull(0)
		if (result == null) {
			return null
		} else {
			return mapper(RowProvider(result))
		}
	}


	/**
	 * @return the rows mapped to the given values or throw an [NoSuchElementException] if no row is available
	 */
	fun <T> getMultiple(mapper: (row: RowProvider) -> T): List<T> {
		val results = results.map { mapper(RowProvider(it)) }
		if (results.isEmpty()) {
			throw NoSuchElementException()
		} else {
			return results
		}
	}


	/**
	 * @return the rows mapped to the given values or an empty list if no row is available
	 */
	fun <T> getMultipleOrNone(mapper: (row: RowProvider) -> T): List<T> {
		return results.map { mapper(RowProvider(it)) }
	}


	/**
	 * @return the amount of returned rows
	 */
	fun getRowCount(): Int {
		return results.size
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
		if (!isOne()) {
			throw NoSuchElementException()
		}
	}

}


class RowProvider(private val row: Map<String, Any?>) {

	fun getShort(column: Column<*>) = getShort(column.columnName)

	fun getShortOrNull(column: Column<*>) = getShortOrNull(column.columnName)

	fun getShort(columName: String): Short {
		val value = getShortOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getShortOrNull(columName: String): Short? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is Short -> value
			is Int -> value.toShort()
			is Long -> value.toShort()
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

	fun getInt(column: Column<*>) = getInt(column.columnName)

	fun getIntOrNull(column: Column<*>) = getIntOrNull(column.columnName)

	fun getInt(columName: String): Int {
		val value = getIntOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getIntOrNull(columName: String): Int? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is Short -> value.toInt()
			is Int -> value
			is Long -> value.toInt()
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

	fun getLong(column: Column<*>) = getLong(column.columnName)

	fun getLongOrNull(column: Column<*>) = getLongOrNull(column.columnName)

	fun getLong(columName: String): Long {
		val value = getLongOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getLongOrNull(columName: String): Long? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is Short -> value.toLong()
			is Int -> value.toLong()
			is Long -> value
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

	fun getFloat(column: Column<*>) = getFloat(column.columnName)

	fun getFloatOrNull(column: Column<*>) = getFloatOrNull(column.columnName)

	fun getFloat(columName: String): Float {
		val value = getFloatOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getFloatOrNull(columName: String): Float? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is Float -> value
			is Double -> value.toFloat()
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

	fun getDouble(column: Column<*>) = getDouble(column.columnName)

	fun getDoubleOrNull(column: Column<*>) = getDoubleOrNull(column.columnName)

	fun getDouble(columName: String): Double {
		val value = getDoubleOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getDoubleOrNull(columName: String): Double? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is Float -> value.toDouble()
			is Double -> value
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

	fun getBoolean(column: Column<*>) = getBoolean(column.columnName)

	fun getBooleanOrNull(column: Column<*>) = getBooleanOrNull(column.columnName)

	fun getBoolean(columName: String): Boolean {
		val value = getBooleanOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getBooleanOrNull(columName: String): Boolean? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is Boolean -> value
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

	fun getString(column: Column<*>) = getString(column.columnName)

	fun getStringOrNull(column: Column<*>) = getStringOrNull(column.columnName)

	fun getString(columName: String): String {
		val value = getStringOrNull(columName)
		if (value == null) {
			throw IllegalStateException("Value of requested column ($columName) is null, column is unknown")
		} else {
			return value
		}
	}

	fun getStringOrNull(columName: String): String? {
		return when (val value = row.getOrDefault(columName, null)) {
			null -> null
			is String -> value
			else -> throw IllegalStateException("Value of requested column ($columName) is of unexpected type")
		}
	}

}