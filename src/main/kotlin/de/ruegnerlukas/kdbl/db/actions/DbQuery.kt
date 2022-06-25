package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database


class DbQuery(
	private val db: Database,
	private val sql: String,
	placeholders: List<String>
) : ParameterStore<DbQuery>(placeholders) {

	override fun provideThis() = this


	/**
	 * Execute the "SELECT"-statement
	 */
	suspend fun execute(): StatementReturningResult {
		val resultSet = db.executeReturning(sql, getParameterValues())
		return StatementReturningResult(resultSet)
	}

}