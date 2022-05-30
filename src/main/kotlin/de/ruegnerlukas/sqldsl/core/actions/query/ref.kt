package de.ruegnerlukas.sqldsl.core.actions.query

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table


//===== TABLE REFS =====//

interface TableRef : QuerySource {
	fun getTableName(): String
	fun getTable(): Table
}

class AliasTableRef<T : Table>(private val table: T, private val alias: String) : TableRef {
	override fun getTableName() = alias
	override fun getTable() = table
}

fun <T : Table> T.alias(alias: String) = AliasTableRef(this, alias)

//===== COLUMN REFS =====//

interface ColumnRef<D, T : Table> : ColumnAttributeExp {
	fun getTableRef(): TableRef
	fun getColumn(): Column<D, T>
}

class AliasColumnRef<D, T : Table>(private val tableRef: AliasTableRef<T>, private val column: Column<D, T>) : ColumnRef<D, T> {
	override fun getTableRef() = tableRef
	override fun getColumn() = column
}

fun <D, T : Table> T.col(block: T.() -> Column<D, T>): AliasColumnRef<D, T> {
	return AliasColumnRef(AliasTableRef(this, this.getTableName()), block(this))
}

fun <D, T : Table> T.col(column: Column<D, T>): AliasColumnRef<D, T> {
	return AliasColumnRef(AliasTableRef(this, this.getTableName()), column)
}


fun <D, T : Table> AliasTableRef<T>.col(block: T.() -> Column<D, T>): AliasColumnRef<D, T> {
	return AliasColumnRef(this, block(this.getTable()))
}

fun <D, T : Table> AliasTableRef<T>.col(column: Column<D, T>): AliasColumnRef<D, T> {
	return AliasColumnRef(this, column)
}
