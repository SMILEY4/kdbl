package de.ruegnerlukas.sqldsl.core.actions.query

import de.ruegnerlukas.sqldsl.core.schema.Table

interface TableRef : QuerySource {
	fun getTableName(): String
	fun getTable(): Table
}

class AliasTableRef<T : Table>(private val table: T, private val alias: String) : TableRef {
	override fun getTableName() = alias
	override fun getTable() = table
}

fun <T : Table> T.alias(alias: String) = AliasTableRef(this, alias)
