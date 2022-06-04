package de.ruegnerlukas.sqldsl2.grammar.query

import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.limit.LimitStatement
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

class QueryStatement(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement? = null,
	val groupBy: GroupByStatement? = null,
	val having: HavingStatement? = null,
	val orderBy: OrderByStatement? = null,
	val limit: LimitStatement? = null,
)


