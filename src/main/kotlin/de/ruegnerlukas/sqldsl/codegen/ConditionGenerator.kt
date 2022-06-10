package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface ConditionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr): Token
}