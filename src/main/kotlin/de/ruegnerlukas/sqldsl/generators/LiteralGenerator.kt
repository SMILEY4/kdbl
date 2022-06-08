package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.expr.LiteralValue
import de.ruegnerlukas.sqldsl.tokens.Token

interface LiteralGenerator {
	fun buildString(e: LiteralValue<*>): String
	fun buildToken(e: LiteralValue<*>): Token
}