package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.expr.Expr
import de.ruegnerlukas.sqldsl2.tokens.Token

interface ExpressionGenerator {
	fun buildString(e: Expr): String
	fun buildToken(e: Expr): Token
}