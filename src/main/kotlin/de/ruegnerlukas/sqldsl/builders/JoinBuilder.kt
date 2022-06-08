package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.grammar.join.UsingJoinConstraint


fun FromExpression.join(right: FromExpression) = JoinBuilder(JoinOp.INNER, this, right)

fun FromExpression.innerJoin(right: FromExpression) = JoinBuilder(JoinOp.INNER, this, right)

fun FromExpression.leftJoin(right: FromExpression) = JoinBuilder(JoinOp.INNER, this, right)

fun FromExpression.rightJoin(right: FromExpression) = JoinBuilder(JoinOp.INNER, this, right)

fun FromExpression.fullJoin(right: FromExpression) = JoinBuilder(JoinOp.INNER, this, right)


class JoinBuilder(private val op: JoinOp, private val left: FromExpression, private val right: FromExpression) {

	fun on(condition: ConditionExpr): JoinClause {
		return JoinClause(op, left, right, ConditionJoinConstraint(condition))
	}

	fun using(columns: List<ColumnExpr<*>>): JoinClause {
		return JoinClause(op, left, right, UsingJoinConstraint(columns))
	}

	fun using(vararg columns: ColumnExpr<*>): JoinClause {
		return JoinClause(op, left, right, UsingJoinConstraint(columns.toList()))
	}

}

