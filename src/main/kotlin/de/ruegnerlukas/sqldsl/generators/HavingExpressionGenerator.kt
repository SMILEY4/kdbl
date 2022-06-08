package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.having.HavingExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

interface HavingExpressionGenerator {
	fun buildString(e: HavingExpression): String
	fun buildToken(e: HavingExpression): Token
}