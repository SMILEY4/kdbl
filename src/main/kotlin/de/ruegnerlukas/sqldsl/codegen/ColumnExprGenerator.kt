package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface ColumnExprGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>): Token
}