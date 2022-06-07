package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.expr.OperationExpr
import de.ruegnerlukas.sqldsl2.tokens.Token

interface OperationExprGenerator {
	fun buildString(e: OperationExpr<*>): String
	fun buildToken(e: OperationExpr<*>): Token
}