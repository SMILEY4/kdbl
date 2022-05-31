package de.ruegnerlukas.sqldsl.core.grammar.statements

class QueryStatement(
	var select: SelectStatement,
	var from: FromStatement,
	var where: WhereStatement?,
	var group: GroupByStatement?,
	var having: HavingStatement?,
	var order: OrderByStatement?,
	var limit: LimitStatement?
)