package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface JoinClauseGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause): Token
}