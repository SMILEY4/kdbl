package de.ruegnerlukas.sqldsl.grammar.orderby

import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression

class OrderByExpression(val column: SelectExpression<*>, val dir: Dir)

enum class Dir {
	ASC,
	DESC
}