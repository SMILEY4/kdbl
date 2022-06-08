package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

interface GroupByExpressionGenerator {
	fun buildString(e: GroupByExpression): String
	fun buildToken(e: GroupByExpression): Token
}