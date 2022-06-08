package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.update.UpdateStatement
import de.ruegnerlukas.sqldsl.tokens.Token

interface UpdateGenerator {
	fun buildString(e: UpdateStatement): String
	fun buildToken(e: UpdateStatement): Token
}