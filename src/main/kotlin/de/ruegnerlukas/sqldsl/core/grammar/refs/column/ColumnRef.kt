package de.ruegnerlukas.sqldsl.core.grammar.refs.column

import de.ruegnerlukas.sqldsl.core.grammar.expression.literal.LiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.select.ColumnSelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.statements.Dir
import de.ruegnerlukas.sqldsl.core.grammar.statements.OrderByEntry
import de.ruegnerlukas.sqldsl.core.schema.Table

interface ColumnRef<D, T : Table> : ColumnSelectExpression, LiteralValue


fun <D, T : Table> ColumnRef<D, T>.asc() = OrderByEntry(this, Dir.ASC)

fun <D, T : Table> ColumnRef<D, T>.desc() = OrderByEntry(this, Dir.DESC)


