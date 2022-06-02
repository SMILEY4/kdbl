package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteGroupByGenerator {

	fun build(groupBy: GroupByStatement): String {
		return buildToken(groupBy).buildString()
	}

	fun buildToken(groupBy: GroupByStatement): Token {
		return ListToken()
			.add("GROUP BY")
			.add(CsvListToken(groupBy.columns.map { SQLiteColumnRefGenerator.build(GenContext.GROUP_BY, it) }))
	}

}