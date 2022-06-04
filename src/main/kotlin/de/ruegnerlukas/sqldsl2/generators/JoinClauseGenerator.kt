package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.tokens.Token

interface JoinClauseGenerator {
	fun buildString(e: JoinClause): String
	fun buildToken(e: JoinClause): Token
}