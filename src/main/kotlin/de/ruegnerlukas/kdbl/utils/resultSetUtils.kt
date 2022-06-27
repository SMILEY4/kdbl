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


/**
 * Returns the first entry or null
 */
fun ResultSet.getFirst(): ResultSet? {
	if (next()) {
		return this
	} else {
		return null
	}
}


/**
 * Returns this result-set as a list of maps
 */
fun ResultSet.toMap(): List<Map<String, Any?>> {
	return map { current ->
		val columnMap = mutableMapOf<String, Any?>()
		for (index in 1..current.metaData.columnCount) {
			val name = current.metaData.getColumnLabel(index)
			val value = current.getObject(index).let { if(current.wasNull()) null else it }
			columnMap[name] = value
		}
		columnMap
	}
}