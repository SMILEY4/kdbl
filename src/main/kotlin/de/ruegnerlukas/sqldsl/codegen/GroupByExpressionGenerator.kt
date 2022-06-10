package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface GroupByExpressionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression): Token
}