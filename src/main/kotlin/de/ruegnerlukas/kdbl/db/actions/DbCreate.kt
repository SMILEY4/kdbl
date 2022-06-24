package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database

/**
 * A single "CREATE"-operations
 * @param db the database to use
 * @param sql the sql-string with possible placeholders
 * @param placeholders the names of the placeholders in the correct order
 */
class DbCreate(db: Database, sql: String, placeholders: List<String>) : DbAction<Unit>(db, sql, placeholders) {

	override suspend fun execute() {
		db.execute(sql, getParameterValues())
	}

}