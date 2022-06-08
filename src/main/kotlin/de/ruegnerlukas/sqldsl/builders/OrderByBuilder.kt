package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression

fun SelectExpression<*>.asc() = OrderByExpression(this, Dir.ASC)

fun SelectExpression<*>.desc() = OrderByExpression(this, Dir.DESC)