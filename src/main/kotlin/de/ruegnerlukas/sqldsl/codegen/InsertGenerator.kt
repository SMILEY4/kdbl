package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface InsertGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement): Token
}