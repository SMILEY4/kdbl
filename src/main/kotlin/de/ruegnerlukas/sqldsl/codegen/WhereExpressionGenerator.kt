package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface WhereExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereExpression): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereExpression): Token
}