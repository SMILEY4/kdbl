package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

interface SelectExpressionGenerator {
	fun buildString(e: SelectExpression<*>): String
	fun buildToken(e: SelectExpression<*>): Token
}