package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface SelectExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>): Token
}