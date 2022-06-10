package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression

fun de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>.asc() =
	de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression(this, de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir.ASC)

fun de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>.desc() =
	de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression(this, de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir.DESC)