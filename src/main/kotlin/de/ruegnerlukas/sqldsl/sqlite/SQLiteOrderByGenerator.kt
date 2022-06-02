package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.statements.Dir
import de.ruegnerlukas.sqldsl.core.syntax.statements.OrderByEntry
import de.ruegnerlukas.sqldsl.core.syntax.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteOrderByGenerator {

	fun build(orderBy: OrderByStatement): String {
		return buildToken(orderBy).buildString()
	}

	fun buildToken(orderBy: OrderByStatement): Token {
		return ListToken()
			.add("ORDER BY")
			.add(CsvListToken(orderBy.entries.map { entry(it) }))
	}

	private fun entry(e: OrderByEntry): Token {
		return ListToken()
			.add(SQLiteColumnRefGenerator.build(GenContext.ORDER_BY, e.column))
			.addIf(e.dir == Dir.ASC) { StringToken("ASC") }
			.addIf(e.dir == Dir.DESC) { StringToken("DESC") }
	}

}