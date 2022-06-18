package de.ruegnerlukas.sqldsl.db.actions

import de.ruegnerlukas.sqldsl.db.Database

class DbQuery(db: Database, sql: String, placeholders: List<String>) : DbAction<DbQueryResult>(db, sql, placeholders) {

	override fun execute(): DbQueryResult {
		return DbQueryResult(db.executeQuery(sql, getParameters()))
	}

}