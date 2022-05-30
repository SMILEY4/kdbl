package de.ruegnerlukas.sqldsl.core.actions.query


fun query() = FromQueryBuilder()


class FromQueryBuilder {

	fun from(vararg source: QuerySource): QueryStatement {
		return QueryStatement(source.toList())
	}

}


