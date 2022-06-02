package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.statements.HavingStatement
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteHavingGenerator {

	fun build(having: HavingStatement): String {
		return buildToken(having).buildString()
	}

	fun buildToken(having: HavingStatement): Token {
		return ListToken()
			.add("HAVING")
			.add(SQLiteExpressionGenerator.buildToken(having.expression))
	}

}