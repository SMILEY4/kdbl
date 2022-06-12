package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.SubQueryExpr

class QueryStatement<T>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement? = null,
	val groupBy: GroupByStatement? = null,
	val having: HavingStatement? = null,
	val orderBy: OrderByStatement? = null,
	val limit: LimitStatement? = null,
): SubQueryExpr<T>, FromElement, InsertContent

interface QueryBuilderEndStep<T> : SubQueryExpr<T>, FromElement, InsertContent {
	fun <T> build(): QueryStatement<T>
}