package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface CreateGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement): Token
}