package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.schema.Table

class TableAliasColumnAliasRef<D, T : Table>(val ref: TableAliasColumnRef<D, T>, val alias: String) : ColumnRef<D, T>

fun <D, T : Table> TableAliasColumnRef<D, T>.alias(alias: String) = TableAliasColumnAliasRef(this, alias)
