package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.where.WhereExpression
import de.ruegnerlukas.sqldsl.tokens.Token

interface WhereExpressionGenerator {
	fun buildString(e: WhereExpression): String
	fun buildToken(e: WhereExpression): Token
}