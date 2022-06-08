package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.tokens.Token

interface ConditionGenerator {
	fun buildString(e: ConditionExpr): String
	fun buildToken(e: ConditionExpr): Token
}