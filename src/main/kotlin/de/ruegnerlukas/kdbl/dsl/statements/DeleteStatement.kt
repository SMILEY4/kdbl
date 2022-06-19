package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.Returning
import de.ruegnerlukas.kdbl.dsl.expression.Table

/**
 * Either the [DeleteStatement] directly or a builder that can produce the statement. For some situations, both can be used interchangeably
 */
sealed interface SqlDeleteStatement


/**
 * A "DELETE"-statement
 */
class DeleteStatement(
	val target: Table,
	val where: Expr<Boolean>? = null,
	val returning: Returning? = null,
) : SqlDeleteStatement


/**
 * A builder that can directly build the [DeleteStatement]
 */
interface DeleteBuilderEndStep : SqlDeleteStatement {
	fun build(): DeleteStatement
}