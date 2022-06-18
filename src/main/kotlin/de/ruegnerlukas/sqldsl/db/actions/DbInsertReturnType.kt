package de.ruegnerlukas.sqldsl.db.actions

import de.ruegnerlukas.sqldsl.db.Database


/**
 * A single "INSERT"-operations
 * @param db the database to use
 * @param sql the sql-string with possible placeholders
 * @param placeholders the names of the placeholders in the correct order
 */
class DbInsertReturnType(private val db: Database, private val sql: String, private val placeholders: List<String>) {

	/**
	 * Return the inserted rows with columns specified in the "returning"-clause
	 */
	fun withReturning() = DbInsertReturning(db, sql, placeholders)


	/**
	 * Return the number of affected rows
	 */
	fun withUpdateCount() = DbInsertCounting(db, sql, placeholders)

}


/**
 * A single "INSERT"-operations that returns rows (i.e. has a "returning"-clause)
 * @param db the database to use
 * @param sql the sql-string with possible placeholders
 * @param placeholders the names of the placeholders in the correct order
 */
class DbInsertReturning(db: Database, sql: String, placeholders: List<String>) : DbAction<DbReturningResult>(db, sql, placeholders) {

	override fun execute(): DbReturningResult {
		return DbReturningResult(db.executeReturning(sql, getParameterValues()))

	}

}


/**
 * A single "INSERT"-operations that returns the number of affected rows
 * @param db the database to use
 * @param sql the sql-string with possible placeholders
 * @param placeholders the names of the placeholders in the correct order
 */
class DbInsertCounting(db: Database, sql: String, placeholders: List<String>) : DbAction<Int>(db, sql, placeholders) {

	override fun execute(): Int {
		return db.executeUpdate(sql, getParameterValues())
	}

}