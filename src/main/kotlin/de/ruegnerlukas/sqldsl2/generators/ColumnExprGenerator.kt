package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.Expr
import de.ruegnerlukas.sqldsl2.tokens.Token

interface ColumnExprGenerator {
	fun buildString(e: ColumnExpr): String
	fun buildToken(e: ColumnExpr): Token
}