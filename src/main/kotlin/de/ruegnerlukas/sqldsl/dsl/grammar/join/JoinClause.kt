package de.ruegnerlukas.sqldsl.dsl.grammar.join

import de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.dsl.grammar.from.JoinFromExpression

enum class JoinOp {
	INNER,
	LEFT,
	RIGHT,
	FULL,
}

class JoinClause(val op: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp, val left: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression, val right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression, val constraint: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinConstraint):
	de.ruegnerlukas.sqldsl.dsl.grammar.from.JoinFromExpression