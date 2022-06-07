package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.expr.LiteralValue
import de.ruegnerlukas.sqldsl2.tokens.Token

interface LiteralGenerator {
	fun buildString(e: LiteralValue<*>): String
	fun buildToken(e: LiteralValue<*>): Token
}