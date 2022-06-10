package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface UpdateGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement): Token
}