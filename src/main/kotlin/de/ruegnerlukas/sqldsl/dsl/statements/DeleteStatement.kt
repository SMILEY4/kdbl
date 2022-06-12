package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.Table

class DeleteStatement(
	val target: Table,
	val where: Expr<Boolean>?,
	val returning: Returning?
)