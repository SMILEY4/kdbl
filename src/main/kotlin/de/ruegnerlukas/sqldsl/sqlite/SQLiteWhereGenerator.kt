package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.statements.WhereStatement
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteWhereGenerator {

	fun build(where: WhereStatement): String {
		return buildToken(where).buildString()
	}

	fun buildToken(where: WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(SQLiteExpressionGenerator.buildToken(where.expression))
	}

}