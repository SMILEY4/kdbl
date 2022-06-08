package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.having.HavingExpression
import de.ruegnerlukas.sqldsl.tokens.Token

interface HavingExpressionGenerator {
	fun buildString(e: HavingExpression): String
	fun buildToken(e: HavingExpression): Token
}