package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface FromExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression): Token
}