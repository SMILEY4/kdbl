package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database

/**
 * A single "SELECT"-operations
 * @param db the database to use
 * @param sql the sql-string with possible placeholders
 * @param placeholders the names of the placeholders in the correct order
 */
class DbQuery(db: Database, sql: String, placeholders: List<String>) : DbAction<DbReturningResult>(db, sql, placeholders) {

	override fun execute(): DbReturningResult {
		return DbReturningResult(db.executeQuery(sql, getParameterValues()))
	}

}