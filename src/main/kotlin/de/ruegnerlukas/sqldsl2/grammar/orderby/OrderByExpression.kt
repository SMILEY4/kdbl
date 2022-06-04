package de.ruegnerlukas.sqldsl2.grammar.orderby

import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.select.SelectExpression

class OrderByExpression(val column: SelectExpression, val dir: Dir) : ColumnExpr

enum class Dir {
	ASC,
	DESC
}