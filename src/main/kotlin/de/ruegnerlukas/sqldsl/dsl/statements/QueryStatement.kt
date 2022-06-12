package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.SubQueryExpr

class QueryStatement<T>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement?,
	val orderBy: OrderByStatement?,
	val limit: LimitStatement?
): SubQueryExpr<T>, FromElement, InsertContent