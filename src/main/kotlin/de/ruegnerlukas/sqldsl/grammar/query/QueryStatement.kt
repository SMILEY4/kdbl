package de.ruegnerlukas.sqldsl.grammar.query

import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertQueryExpression
import de.ruegnerlukas.sqldsl.grammar.limit.LimitStatement
import de.ruegnerlukas.sqldsl.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement

class QueryStatement(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement? = null,
	val groupBy: GroupByStatement? = null,
	val having: HavingStatement? = null,
	val orderBy: OrderByStatement? = null,
	val limit: LimitStatement? = null,
) : QueryFromExpression, InsertQueryExpression


