package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl.tokens.Token

interface DeleteGenerator {
	fun buildString(e: DeleteStatement): String
	fun buildToken(e: DeleteStatement): Token
}