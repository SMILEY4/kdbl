package de.ruegnerlukas.sqldsl2.schema

import de.ruegnerlukas.sqldsl2.grammar.TableLike
import de.ruegnerlukas.sqldsl2.grammar.from.TableFromExpression

abstract class Table<T>(val tableName: String, val create: Create = Create.IF_NOT_EXISTS) : TableFromExpression, TableLike {

	private val columns = mutableListOf<Column>()

	fun register(column: Column) {
		columns.add(column)
	}

	fun getTableColumns() = columns.toList()

	abstract fun alias(alias: String): T

}


abstract class UnknownTable : Table<Nothing>("", Create.ALWAYS)