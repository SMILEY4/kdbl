package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expression.DataType
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionType
import de.ruegnerlukas.sqldsl.dsl.expression.JoinOp
import de.ruegnerlukas.sqldsl.dsl.statements.Dir
import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

/**
 * Sql-dialect specific code-generation
 */
interface SQLDialect {

	/**
	 * representation of a data type
	 */
	fun dataType(type: DataType): String


	/**
	 * literal representation of a boolean value
	 */
	fun booleanLiteral(value: Boolean): String


	/**
	 * literal representation of a string value
	 */
	fun stringLiteral(value: String): String


	/**
	 * literal representation of a date value
	 */
	fun dateLiteral(value: SqlDate): String


	/**
	 * literal representation of a time value
	 */
	fun timeLiteral(value: SqlTime): String


	/**
	 * single field in an "order-by"-statement.
	 * Takes care of e.g. priority of "NULL"-values -> always nulls first, i.e. null should be smaller than any other value
	 */
	fun orderField(field: Token, strDirection: String, dir: Dir): Token


	/**
	 * Handle an auto-incrementing column
	 * @param dataType the original data type of the column
	 * @param isPrimaryKey whether the column is a primary-key
	 * @param fnReplaceColumnType call this function to replace the data-type with the given string
	 * @param fnInsertConstraint call this function to insert the given string as a column constraint.
	 * @param fnForbidAutoIncrement to not allow this column to be auto-incrementing
	 */
	fun autoIncrementColumn(
		dataType: DataType,
		isPrimaryKey: Boolean,
		fnReplaceColumnType: (type: String) -> Unit,
		fnInsertConstraint: (constraint: String) -> Unit,
		fnForbidAutoIncrement: () -> Unit
	)


	/**
	 * Map the join operation to a string. Return null if op is not supported.
	 */
	fun joinOperation(op: JoinOp): String?


	/**
	 * @return the name of the given function (or null if not supported)
	 */
	fun functionName(f: FunctionType): String?


	/**
	 * Return non-null token to overwrite default function builder
	 */
	fun function(type: FunctionType, args: List<Expr<*>>, exprBuilder: (e: Expr<*>) -> Token): Token?

}