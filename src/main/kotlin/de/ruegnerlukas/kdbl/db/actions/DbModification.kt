package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database


class DbModification(
	private val db: Database,
	private val sql: String,
	placeholders: List<String>
) : ParameterStore<DbModification>(placeholders) {

	override fun provideThis() = this


	/**
	 * Execute the "INSERT", "UPDATE" or "DELETE"-statement
	 */
	suspend fun execute() {
		executeCounting()
	}

	/**
	 * Execute the "INSERT", "UPDATE" or "DELETE"-statement and return the amount of affected rows
	 */
	suspend fun executeCounting(): Int {
		return db.executeUpdate(sql, getParameterValues())
	}


	/**
	 * Execute the "INSERT", "UPDATE" or "DELETE"-statement and return the result of a "RETURNING"-clause
	 */
	suspend fun executeReturning(): StatementReturningResult {
		val resultSet = db.executeReturning(sql, getParameterValues())
		return StatementReturningResult(resultSet)
	}

}