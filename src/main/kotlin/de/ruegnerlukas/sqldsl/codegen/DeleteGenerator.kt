package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface DeleteGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement): Token
}