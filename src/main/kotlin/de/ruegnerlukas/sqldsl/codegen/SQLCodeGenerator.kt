package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement

/**
 * Generates sql-strings from statements (in the form of tokens that can be directly converted to string)
 */
interface SQLCodeGenerator {

	/**
	 * generate a "CREATE"-statement
	 */
	fun create(create: CreateTableStatement): Token


	/**
	 * generate an "INSERT"-statement
	 */
	fun insert(insert: InsertStatement): Token


	/**
	 * generate an "UPDATE"-statement
	 */
	fun update(update: UpdateStatement): Token


	/**
	 * generate a "DELETE"-statement
	 */
	fun delete(delete: DeleteStatement): Token


	/**
	 * generate a "SELECT"-statement
	 */
	fun query(query: QueryStatement<*>): Token
}