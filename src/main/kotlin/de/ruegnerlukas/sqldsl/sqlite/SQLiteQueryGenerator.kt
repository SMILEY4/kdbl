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
	}

}