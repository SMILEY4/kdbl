package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.dsl.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.dsl.grammar.join.UsingJoinConstraint


fun de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression.join(right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression) = JoinBuilder(
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER, this, right)

fun de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression.innerJoin(right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression) = JoinBuilder(
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER, this, right)

fun de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression.leftJoin(right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression) = JoinBuilder(
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER, this, right)

fun de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression.rightJoin(right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression) = JoinBuilder(
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER, this, right)

fun de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression.fullJoin(right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression) = JoinBuilder(
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER, this, right)


class JoinBuilder(private val op: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp, private val left: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression, private val right: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression) {

	fun on(condition: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr): de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause {
		return de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause(
			op,
			left,
			right,
			de.ruegnerlukas.sqldsl.dsl.grammar.join.ConditionJoinConstraint(condition)
		)
	}

	fun using(columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>>): de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause {
		return de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause(
			op,
			left,
			right,
			de.ruegnerlukas.sqldsl.dsl.grammar.join.UsingJoinConstraint(columns)
		)
	}

	fun using(vararg columns: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>): de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause {
		return de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause(
			op,
			left,
			right,
			de.ruegnerlukas.sqldsl.dsl.grammar.join.UsingJoinConstraint(columns.toList())
		)
	}

}

