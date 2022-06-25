package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database


class DbCreate(
	private val db: Database,
	private val sql: String,
	placeholders: List<String>
) : ParameterStore<DbCreate>(placeholders) {

	/**
	 * Execute the "CREATE"-statement
	 */
	suspend fun execute() {
		db.execute(sql, listOf())
	}

	override fun provideThis() = this

}