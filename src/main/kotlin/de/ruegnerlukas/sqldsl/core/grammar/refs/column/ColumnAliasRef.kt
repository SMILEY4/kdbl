package de.ruegnerlukas.sqldsl.core.grammar.refs.column

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table

class ColumnAliasRef<D, T : Table>(val column: Column<D, T>, val alias: String) : ColumnRef<D, T>

fun <D, T : Table> Column<D, T>.alias(alias: String) = ColumnAliasRef(this, alias)
