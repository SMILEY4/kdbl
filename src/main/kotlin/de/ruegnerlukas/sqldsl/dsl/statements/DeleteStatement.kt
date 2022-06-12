package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.Table

class DeleteStatement(
	val target: Table,
	val where: Expr<Boolean>? = null,
	val returning: Returning? = null,
)

interface DeleteBuilderEndStep {
	fun build(): DeleteStatement
}