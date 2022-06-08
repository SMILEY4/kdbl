package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.tokens.Token

interface ExpressionGenerator {
	fun buildString(e: Expr<*>): String
	fun buildToken(e: Expr<*>): Token
}