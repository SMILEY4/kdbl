package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface AggregateFunctionGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<*>): Token
}