package de.ruegnerlukas.sqldsl.dsl.grammar.orderby

import de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression

class OrderByExpression(val column: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>, val dir: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir)

enum class Dir {
	ASC,
	DESC
}