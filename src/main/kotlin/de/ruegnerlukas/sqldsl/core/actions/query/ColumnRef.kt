package de.ruegnerlukas.sqldsl.core.actions.query

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table


interface ColumnRef<D, T : Table> : ColumnAttributeExp, SelectColumn {
	fun getTableRef(): TableRef
	fun getColumn(): Column<D, T>
	fun getColumnName(): String
}

class AliasTableColumnRef<D, T : Table>(private val tableRef: AliasTableRef<T>, private val column: Column<D, T>) : ColumnRef<D, T> {
	override fun getTableRef() = tableRef
	override fun getColumn() = column
	override fun getColumnName() = column.getColumnName()
}

operator fun <D, T : Table> T.get(block: T.() -> Column<D, T>): AliasTableColumnRef<D, T> {
	return AliasTableColumnRef(AliasTableRef(this, this.getTableName()), block(this))
}

operator fun <D, T : Table> T.get(column: Column<D, T>): AliasTableColumnRef<D, T> {
	return AliasTableColumnRef(AliasTableRef(this, this.getTableName()), column)
}

operator fun  <D, T : Table> AliasTableRef<T>.get(column: Column<D, T>): AliasTableColumnRef<D, T> {
	return AliasTableColumnRef(this, column)
}

operator fun  <D, T : Table> AliasTableRef<T>.get(block: T.() -> Column<D, T>): AliasTableColumnRef<D, T> {
	return AliasTableColumnRef(this, block(this.getTable()))
}

