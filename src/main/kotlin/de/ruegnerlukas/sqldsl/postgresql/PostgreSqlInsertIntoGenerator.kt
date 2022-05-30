package de.ruegnerlukas.sqldsl.postgresql

import de.ruegnerlukas.sqldsl.core.actions.insert.InsertStatement
import de.ruegnerlukas.sqldsl.core.generators.InsertIntoGenerator
import de.ruegnerlukas.sqldsl.core.schema.OnConflict
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class PostgreSqlInsertIntoGenerator : InsertIntoGenerator {

	override fun build(stmt: InsertStatement): String {
		return ListToken()
			.add("INSERT INTO")
			.add(stmt.table.getTableName())
			.add(
				GroupToken(
					CsvListToken(
						stmt.columns.map { it.getName() }.map { StringToken(it) }
					),
				)
			)
			.add("VALUES")
			.add(
				CsvListToken(
					stmt.items.map { itemEntry(stmt, it) },
				)
			)
			.add(conflict(stmt))
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

	private fun conflict(stmt: InsertStatement): Token {
		return if (stmt.onConflict == OnConflict.ABORT || stmt.onConflict == OnConflict.FAIL) {
			NoOpToken()
		} else {
			StringToken("ON CONFLICT ${mapOnConflict(stmt.onConflict)}")
		}
	}

	private fun mapOnConflict(action: OnConflict): String {
		return when (action) {
			OnConflict.IGNORE -> "DO NOTHING"
			OnConflict.ROLLBACK -> "DO NOTHING"
			OnConflict.REPLACE -> "DO UPDATE"
			OnConflict.ABORT -> ""
			OnConflict.FAIL -> ""
		}
	}


}