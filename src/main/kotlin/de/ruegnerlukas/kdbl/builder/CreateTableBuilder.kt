package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.statements.CreateTableStatement

/**
 * Builder for [CreateTableStatement]
 */
class CreateTableBuilder {

	/**
	 * Create the given table
	 */
	fun create(table: Table) = CreateTableStatement(table, false)


	/**
	 * Create the given table, only if it does not exist yet
	 */
	fun createIfNotExists(table: Table) = CreateTableStatement(table, true)

}