package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface HavingExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingExpression): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingExpression): Token
}