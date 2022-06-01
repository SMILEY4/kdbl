package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.LiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.select.ColumnSelectExpression
import de.ruegnerlukas.sqldsl.core.syntax.statements.Dir
import de.ruegnerlukas.sqldsl.core.syntax.statements.OrderByEntry
import de.ruegnerlukas.sqldsl.core.schema.Table

interface ColumnRef<D, T : Table> : ColumnSelectExpression, LiteralValue<D>


fun <D, T : Table> ColumnRef<D, T>.asc() = OrderByEntry(this, Dir.ASC)

fun <D, T : Table> ColumnRef<D, T>.desc() = OrderByEntry(this, Dir.DESC)


