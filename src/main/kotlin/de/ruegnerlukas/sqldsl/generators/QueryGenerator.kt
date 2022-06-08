package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.tokens.Token

interface QueryGenerator {
	fun buildString(e: QueryStatement): String
	fun buildToken(e: QueryStatement): Token
}