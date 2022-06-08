package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

interface FromExpressionGenerator {
	fun buildString(e: FromExpression): String
	fun buildToken(e: FromExpression): Token
}