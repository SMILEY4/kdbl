package de.ruegnerlukas.sqldsl2.grammar.update

import de.ruegnerlukas.sqldsl2.grammar.expr.Expr
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl2.schema.OnConflict

class UpdateStatement(
	val onConflict: OnConflict,
	val target: CommonTarget,
	val set: UpdateSetStatement,
	val from : UpdateFromExpression? = null,
	val where: UpdateCondition? = null,
	val returning: ReturningStatement? = null
)


class UpdateSetStatement(val expressions: List<UpdateExpression>)

class UpdateExpression(val column: UpdateColumn, val value: Expr)

interface UpdateColumn

class UpdateFromExpression()

interface UpdateCondition