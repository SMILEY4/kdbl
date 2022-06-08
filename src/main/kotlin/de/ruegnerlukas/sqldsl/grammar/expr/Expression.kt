package de.ruegnerlukas.sqldsl.grammar.expr

import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl.grammar.select.ExprSelectExpression
import de.ruegnerlukas.sqldsl.schema.AnyValueType

interface Expr<T: AnyValueType> : ExprSelectExpression<T>, GroupByExpression
