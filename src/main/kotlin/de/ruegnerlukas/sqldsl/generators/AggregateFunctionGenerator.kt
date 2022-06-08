package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.expr.AggregateFunction
import de.ruegnerlukas.sqldsl.tokens.Token

interface AggregateFunctionGenerator {
	fun buildString(e: AggregateFunction<*>): String
	fun buildToken(e: AggregateFunction<*>): Token
}