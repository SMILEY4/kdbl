package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.where.WhereExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

interface WhereExpressionGenerator {
	fun buildString(e: WhereExpression): String
	fun buildToken(e: WhereExpression): Token
}