package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface ExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*>): Token
}