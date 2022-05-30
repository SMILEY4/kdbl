package de.ruegnerlukas.sqldsl.core.schema

open class Table(private val name: String, private val create: Create = Create.ALWAYS) {

	private val columns = mutableListOf<Column<*>>()

	fun register(column: Column<*>) {
		columns.add(column)
	}

	fun getTableName() = name

	fun getCreatePolicy() = create

	fun getTableColumns() = columns.toList()

}