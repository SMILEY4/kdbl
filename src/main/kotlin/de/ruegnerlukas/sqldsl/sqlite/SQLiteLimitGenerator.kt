package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.statements.LimitStatement
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteLimitGenerator {

	fun build(limit: LimitStatement): String {
		return buildToken(limit).buildString()
	}

	fun buildToken(limit: LimitStatement): Token {
		return ListToken()
			.add("LIMIT")
			.add(limit.limit.toString())
	}

}