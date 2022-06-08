package de.ruegnerlukas.sqldsl.grammar.join

import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.grammar.from.JoinFromExpression

enum class JoinOp {
	INNER,
	LEFT,
	RIGHT,
	FULL,
}

class JoinClause(val op: JoinOp, val left: FromExpression, val right: FromExpression, val constraint: JoinConstraint): JoinFromExpression