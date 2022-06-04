package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.tokens.Token

interface QueryGenerator {
	fun buildString(e: QueryStatement): String
	fun buildToken(e: QueryStatement): Token
}