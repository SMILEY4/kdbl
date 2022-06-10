package de.ruegnerlukas.sqldsl.dsl.schema

import de.ruegnerlukas.sqldsl.dsl.grammar.column.Column


abstract class Table<T>(override val tableName: String) : de.ruegnerlukas.sqldsl.dsl.grammar.table.StandardTable {

	private val columns = mutableListOf<Column<*>>()

	fun register(column: Column<*>) {
		columns.add(column)
	}

	fun getTableColumns() = columns.toList()

	abstract fun alias(alias: String): T

}


abstract class UnknownTable : Table<Nothing>("")