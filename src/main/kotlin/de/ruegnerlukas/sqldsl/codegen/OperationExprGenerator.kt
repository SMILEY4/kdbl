package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface OperationExprGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<*>): Token
}