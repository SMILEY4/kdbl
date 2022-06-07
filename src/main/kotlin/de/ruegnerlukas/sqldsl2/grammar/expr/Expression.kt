package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl2.grammar.select.ExprSelectExpression
import de.ruegnerlukas.sqldsl2.schema.AnyValueType

interface Expr<T: AnyValueType> : ExprSelectExpression<T>, GroupByExpression
