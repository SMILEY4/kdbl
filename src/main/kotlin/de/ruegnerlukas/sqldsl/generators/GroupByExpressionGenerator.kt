package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl.tokens.Token

interface GroupByExpressionGenerator {
	fun buildString(e: GroupByExpression): String
	fun buildToken(e: GroupByExpression): Token
}