package de.ruegnerlukas.sqldsl.dsl.grammar.query

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType

class QueryStatement<T: AnyValueType>(
	val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement,
	val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement,
	val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement? = null,
	val groupBy: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement? = null,
	val having: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingStatement? = null,
	val orderBy: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement? = null,
	val limit: de.ruegnerlukas.sqldsl.dsl.grammar.limit.LimitStatement? = null,
) : de.ruegnerlukas.sqldsl.dsl.grammar.from.QueryFromExpression, de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertQueryExpression,
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubQueryLiteral<T>


