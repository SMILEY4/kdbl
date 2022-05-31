package de.ruegnerlukas.sqldsl.core.grammar.from

import de.ruegnerlukas.sqldsl.core.grammar.join.JoinConstraint
import de.ruegnerlukas.sqldsl.core.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.core.grammar.statements.QueryStatement
import de.ruegnerlukas.sqldsl.core.schema.Table


interface FromExpression

class TableFromExpression(val table: Table, val alias: String) : FromExpression

class QueryFromExpression(val query: QueryStatement, val alias: String) : FromExpression

class JoinFromExpression(
	val left: FromExpression,
	val right: FromExpression,
	val joinOp: JoinOp,
	val constraint: JoinConstraint,
) : FromExpression