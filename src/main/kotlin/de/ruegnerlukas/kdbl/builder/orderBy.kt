package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.statements.Dir
import de.ruegnerlukas.kdbl.dsl.statements.OrderByElement

fun Expr<*>.asc() = OrderByElement(this, Dir.ASC)
fun Expr<*>.desc() = OrderByElement(this, Dir.ASC)