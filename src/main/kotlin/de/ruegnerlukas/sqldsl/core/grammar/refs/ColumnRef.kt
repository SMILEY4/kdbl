package de.ruegnerlukas.sqldsl.core.grammar.refs

import de.ruegnerlukas.sqldsl.core.grammar.expression.LiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.statements.Dir
import de.ruegnerlukas.sqldsl.core.grammar.statements.OrderByEntry
import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table

interface ColumnRef<D, T : Table> : SelectExpression, LiteralValue

interface DirectColumnRef<D, T : Table> : ColumnRef<D, T>

class ColumnAliasRef<D, T : Table>(val column: Column<D, T>, val alias: String) : ColumnRef<D, T>

class TableAliasColumnRef<D, T : Table>(val ref: AliasTableRef<T>, val column: Column<D, T>) : ColumnRef<D, T>

class TableAliasColumnAliasRef<D, T : Table>(val ref: TableAliasColumnRef<D, T>, val alias: String) : ColumnRef<D, T>


operator fun <D, T : Table> AliasTableRef<T>.get(column: Column<D, T>) = TableAliasColumnRef(this, column)

fun <D, T : Table> Column<D, T>.alias(alias: String) = ColumnAliasRef(this, alias)

fun <D, T : Table> TableAliasColumnRef<D, T>.alias(alias: String) = TableAliasColumnAliasRef(this, alias)

fun <D, T : Table> ColumnRef<D, T>.asc() = OrderByEntry(this, Dir.ASC)

fun <D, T : Table> ColumnRef<D, T>.desc() = OrderByEntry(this, Dir.DESC)
