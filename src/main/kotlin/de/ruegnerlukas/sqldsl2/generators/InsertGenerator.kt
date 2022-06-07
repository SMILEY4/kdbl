package de.ruegnerlukas.sqldsl2.generators

import de.ruegnerlukas.sqldsl2.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl2.tokens.Token

interface InsertGenerator {
	fun buildString(e: InsertStatement): String
	fun buildToken(e: InsertStatement): Token
}