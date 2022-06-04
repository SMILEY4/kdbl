package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl2.grammar.select.ExprSelectExpression

interface Expr : ExprSelectExpression, GroupByExpression
