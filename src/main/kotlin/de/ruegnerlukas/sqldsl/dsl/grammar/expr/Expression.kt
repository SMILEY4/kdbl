package de.ruegnerlukas.sqldsl.dsl.grammar.expr

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType

interface Expr<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.select.ExprSelectExpression<T>,
	de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression
