package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Table

/**
 * A "CREATE"-statement
 * @param table the table to create
 *  @param onlyIfNotExists whether to only create the table when it does not exist yet
 */
class CreateTableStatement(val table: Table, val onlyIfNotExists: Boolean) : SqlStatement
