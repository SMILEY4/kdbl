package de.ruegnerlukas.sqldsl.utils

import java.sql.ResultSet

fun ResultSet.forEach(block: (e: ResultSet) -> Unit) {
	while (next()) {
		block(this)
	}
}

fun <T> ResultSet.map(block: (e: ResultSet) -> T): List<T> {
	val list = mutableListOf<T>()
	this.forEach {
		list.add(block(this))
	}
	return list
}