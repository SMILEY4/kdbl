package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface OrderByExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): Token
}