package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.SubQueryExpr

interface SqlQueryStatement<T>

class QueryStatement<T>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement? = null,
	val groupBy: GroupByStatement? = null,
	val having: HavingStatement? = null,
	val orderBy: OrderByStatement? = null,
	val limit: LimitStatement? = null,
): SqlQueryStatement<T>, SubQueryExpr<T>, FromElement, InsertContent

interface QueryBuilderEndStep<T> : SqlQueryStatement<T>, SubQueryExpr<T>, FromElement, InsertContent {
	fun <T> build(): QueryStatement<T>
}