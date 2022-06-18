package de.ruegnerlukas.sqldsl.db.actions

import de.ruegnerlukas.sqldsl.db.Database

class DbUpdate(db: Database, sql: String, placeholders: List<String>) : DbAction<Int>(db, sql, placeholders) {

	override fun execute(): Int {
		return db.executeUpdate(sql, getParameters())
	}

}