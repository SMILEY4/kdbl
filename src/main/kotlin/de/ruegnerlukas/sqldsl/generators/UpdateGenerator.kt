package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.update.UpdateStatement
import de.ruegnerlukas.sqldsl2.tokens.Token

interface UpdateGenerator {
	fun buildString(e: UpdateStatement): String
	fun buildToken(e: UpdateStatement): Token
}