package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.builders.insert.InsertStatement
import de.ruegnerlukas.sqldsl.core.generators.InsertIntoGenerator
import de.ruegnerlukas.sqldsl.core.schema.OnConflict
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SQLiteInsertIntoGenerator : InsertIntoGenerator {

	override fun build(stmt: InsertStatement): String {
		return ListToken()
			.add("INSERT")
			.addIf("OR ${mapOnConflict(stmt.onConflict)}") { stmt.onConflict != OnConflict.ABORT }
			.add("INTO")
			.add(stmt.table.getTableName())
			.add(
				GroupToken(
					CsvListToken(
						stmt.columns.map { it.getColumnName() }.map { StringToken(it) }
					),
				)
			)
			.add("VALUES")
			.add(
				CsvListToken(
					stmt.items.map { itemEntry(stmt, it) },
				)
			)
			.buildString()
	}

	private fun itemEntry(stmt: InsertStatement, item: InsertStatement.Item): Token {
		return GroupToken(
			CsvListToken(
				stmt.columns.map {
					val value = item.get(it)
					if (value == null) {
						StringToken("NULL")
					} else {
						StringToken(value.asString())
					}
				}
			)
		)
	}

	private fun mapOnConflict(action: OnConflict): String {
		return when (action) {
			OnConflict.ABORT -> "ABORT"
			OnConflict.FAIL -> "FAIL"
			OnConflict.IGNORE -> "IGNORE"
			OnConflict.REPLACE -> "REPLACE"
			OnConflict.ROLLBACK -> "ROLLBACK"
		}
	}


}