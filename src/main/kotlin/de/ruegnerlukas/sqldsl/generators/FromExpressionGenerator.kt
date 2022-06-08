package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.tokens.Token

interface FromExpressionGenerator {
	fun buildString(e: FromExpression): String
	fun buildToken(e: FromExpression): Token
}