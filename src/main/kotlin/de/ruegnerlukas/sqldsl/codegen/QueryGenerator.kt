package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

interface QueryGenerator {
	fun buildString(e: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>): String
	fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>): Token
}