package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.statements.QueryStatement
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteQueryGenerator {

	fun build(query: QueryStatement): String {
		return buildToken(query).buildString()
	}

	fun buildToken(query: QueryStatement): Token {
		return ListToken()
			.add(SQLiteSelectGenerator.buildToken(query.select))
			.add(SQLiteFromGenerator.buildToken(query.from))
			.addIf(query.where != null) { SQLiteWhereGenerator.buildToken(query.where!!) }
			.addIf(query.group != null) { SQLiteGroupByGenerator.buildToken(query.group!!) }
			.addIf(query.having != null) { SQLiteHavingGenerator.buildToken(query.having!!) }
			.addIf(query.order != null) { SQLiteOrderByGenerator.buildToken(query.order!!) }
			.addIf(query.limit != null) { SQLiteLimitGenerator.buildToken(query.limit!!) }
	}

}