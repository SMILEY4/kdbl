package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.tokens.Token

interface SelectExpressionGenerator {
	fun buildString(e: SelectExpression<*>): String
	fun buildToken(e: SelectExpression<*>): Token
}