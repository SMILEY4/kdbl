package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl.tokens.Token

interface OrderByExpressionGenerator {
	fun buildString(e: OrderByExpression): String
	fun buildToken(e: OrderByExpression): Token
}