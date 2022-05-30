package de.ruegnerlukas.sqldsl.core.actions.query

interface QuerySource


enum class JoinType {
	INNER,
	LEFT,
	CROSS
}


/**
 * represents a join as a source
 */
class JoinClauseQuerySource(val type: JoinType, val source: QuerySource, val other: QuerySource, val onExpression: QueryExp) : QuerySource


fun innerJoin(source: QuerySource, other: QuerySource, on: QueryExp): JoinClauseQuerySource {
	return JoinClauseQuerySource(JoinType.INNER, source, other, on)
}

fun leftJoin(source: QuerySource, other: QuerySource, on: QueryExp): JoinClauseQuerySource {
	return JoinClauseQuerySource(JoinType.LEFT, source, other, on)
}

fun crossJoin(source: QuerySource, other: QuerySource, on: QueryExp): JoinClauseQuerySource {
	return JoinClauseQuerySource(JoinType.CROSS, source, other, on)
}



