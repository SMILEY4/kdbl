package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl2.tokens.Token

interface DeleteGenerator {
	fun buildString(e: DeleteStatement): String
	fun buildToken(e: DeleteStatement): Token
}