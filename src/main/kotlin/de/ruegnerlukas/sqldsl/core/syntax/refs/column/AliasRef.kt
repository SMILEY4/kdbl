package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.schema.UnknownTable
import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression

interface AliasRef<D, T : Table> : ColumnRef<D, T>

class ColumnAliasRef<D, T : Table>(val column: Column<D, T>, val alias: String) : AliasRef<D, T>

fun <D, T : Table> Column<D, T>.alias(alias: String) = ColumnAliasRef(this, alias)


class ExpressionAliasRef<D>(val expression: Expression<D>, val alias: String) : AliasRef<D, UnknownTable>

fun <T> Expression<T>.alias(alias: String) = ExpressionAliasRef(this, alias)