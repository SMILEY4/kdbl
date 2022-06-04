package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

interface OrderByExpressionGenerator {
	fun buildString(e: OrderByExpression): String
	fun buildToken(e: OrderByExpression): Token
}