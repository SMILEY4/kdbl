package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database
import de.ruegnerlukas.kdbl.utils.toMap


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
		val result = db.executeReturning(sql, getParameterValues()) { result ->
			result.toMap()
		}
		return StatementReturningResult(result)
	}

}