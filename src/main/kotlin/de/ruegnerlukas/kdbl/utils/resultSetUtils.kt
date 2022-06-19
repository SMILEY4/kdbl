package de.ruegnerlukas.kdbl.utils

import java.sql.ResultSet

/**
 * Iterate over each "entry" in this result-set
 */
fun ResultSet.forEach(block: (e: ResultSet) -> Unit) {
	while (next()) {
		block(this)
	}
}

/**
 * Maps each "entry" in this result-set and returns all as a list
 */
fun <T> ResultSet.map(block: (e: ResultSet) -> T): List<T> {
	val list = mutableListOf<T>()
	this.forEach {
		list.add(block(this))
	}
	return list
}