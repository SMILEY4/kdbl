package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface LiteralGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>): Token
}