package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.tokens.Token

interface JoinClauseGenerator {
	fun buildString(e: JoinClause): String
	fun buildToken(e: JoinClause): Token
}