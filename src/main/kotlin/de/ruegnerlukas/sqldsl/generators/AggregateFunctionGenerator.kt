package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.expr.AggregateFunction
import de.ruegnerlukas.sqldsl2.tokens.Token

interface AggregateFunctionGenerator {
	fun buildString(e: AggregateFunction<*>): String
	fun buildToken(e: AggregateFunction<*>): Token
}