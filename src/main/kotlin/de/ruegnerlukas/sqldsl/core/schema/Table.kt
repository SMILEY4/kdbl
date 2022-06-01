package de.ruegnerlukas.sqldsl.core.schema

import de.ruegnerlukas.sqldsl.core.grammar.refs.DirectTableRef


open class Table(
	private val name: String,
	private val create: Create = Create.ALWAYS
): DirectTableRef {

	private val columns = mutableListOf<Column<*, *>>()

	fun register(column: Column<*, *>) {
		columns.add(column)
	}

	fun getTableName() = name

	fun getCreatePolicy() = create

	fun getTableColumns() = columns.toList()

}