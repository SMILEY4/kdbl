package de.ruegnerlukas.sqldsl.core.actions.query

data class QueryStatement(
	val selectColumns: List<SelectColumn>,
	val querySources: List<QuerySource>,
	val orderBy: List<Pair<ColumnRef<*,*>, Dir>>
)