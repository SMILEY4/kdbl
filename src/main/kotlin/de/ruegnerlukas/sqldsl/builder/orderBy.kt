package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.statements.Dir
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByElement

fun Expr<*>.asc() = OrderByElement(this, Dir.ASC)
fun Expr<*>.desc() = OrderByElement(this, Dir.ASC)