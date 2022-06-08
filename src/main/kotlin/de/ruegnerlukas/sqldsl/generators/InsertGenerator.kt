package de.ruegnerlukas.sqldsl.generators

import de.ruegnerlukas.sqldsl.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl.tokens.Token

interface InsertGenerator {
	fun buildString(e: InsertStatement): String
	fun buildToken(e: InsertStatement): Token
}