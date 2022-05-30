package de.ruegnerlukas.sqldsl.core.schema

import de.ruegnerlukas.sqldsl.core.actions.query.TableRef

open class Table(private val name: String, private val create: Create = Create.ALWAYS) : TableRef {

	private val columns = mutableListOf<Column<*, *>>()

	fun register(column: Column<*, *>) {
		columns.add(column)
	}

	override fun getTableName() = name

	override fun getTable() = this

	fun getCreatePolicy() = create

	fun getTableColumns() = columns.toList()

}