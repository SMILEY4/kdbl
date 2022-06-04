package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl2.tokens.Token

interface ConditionGenerator {
	fun buildString(e: ConditionExpr): String
	fun buildToken(e: ConditionExpr): Token
}