package de.ruegnerlukas.sqldsl.schema

import de.ruegnerlukas.sqldsl.grammar.table.StandardTable


abstract class Table<T>(override val tableName: String) : StandardTable {

	private val columns = mutableListOf<Column<*>>()

	fun register(column: Column<*>) {
		columns.add(column)
	}

	fun getTableColumns() = columns.toList()

	abstract fun alias(alias: String): T

}


abstract class UnknownTable : Table<Nothing>("")