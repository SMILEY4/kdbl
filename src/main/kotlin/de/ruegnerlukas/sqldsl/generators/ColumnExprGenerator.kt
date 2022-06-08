package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.tokens.Token

interface ColumnExprGenerator {
	fun buildString(e: ColumnExpr<*>): String
	fun buildToken(e: ColumnExpr<*>): Token
}