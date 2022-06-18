package de.ruegnerlukas.sqldsl.db.actions

import de.ruegnerlukas.sqldsl.db.Database

class DbCreate(db: Database, sql: String, placeholders: List<String>) : DbAction<Unit>(db, sql, placeholders) {

	override fun execute() {
		db.execute(sql, getParameters())
	}

}