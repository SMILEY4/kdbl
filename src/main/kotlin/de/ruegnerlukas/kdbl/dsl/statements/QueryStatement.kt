package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.SubQueryExpr

/**
 * Either the [QueryStatement] directly or a builder that can produce the statement. For some situations, both can be used interchangeably
 */
sealed interface SqlQueryStatement<T>


/**
 * A query- / "SELECT-Statement
 */
class QueryStatement<T>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement? = null,
	val groupBy: GroupByStatement? = null,
	val having: HavingStatement? = null,
	val orderBy: OrderByStatement? = null,
	val limit: LimitStatement? = null,
) : SqlQueryStatement<T>, SubQueryExpr<T>, FromElement, InsertContent


/**
 * A builder that can directly build the [QueryStatement]
 */
interface QueryBuilderEndStep<T> : SqlQueryStatement<T>, SubQueryExpr<T>, FromElement, InsertContent {
	fun <T> build(): QueryStatement<T>
}