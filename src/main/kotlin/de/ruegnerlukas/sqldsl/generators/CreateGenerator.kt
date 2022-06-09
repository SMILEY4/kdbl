package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.create.CreateTableStatement
import de.ruegnerlukas.sqldsl.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl.tokens.Token

interface CreateGenerator {
	fun buildString(e: CreateTableStatement): String
	fun buildToken(e: CreateTableStatement): Token
}