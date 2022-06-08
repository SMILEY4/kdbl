package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.expr.OperationExpr
import de.ruegnerlukas.sqldsl.tokens.Token

interface OperationExprGenerator {
	fun buildString(e: OperationExpr<*>): String
	fun buildToken(e: OperationExpr<*>): Token
}