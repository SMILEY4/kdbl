package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.JoinElement
import de.ruegnerlukas.kdbl.dsl.expression.JoinOp
import de.ruegnerlukas.kdbl.dsl.expression.OnJoinCondition
import de.ruegnerlukas.kdbl.dsl.expression.UsingJoinCondition
import de.ruegnerlukas.kdbl.dsl.statements.FromElement

fun FromElement.join(other: FromElement) = JoinConditionBuilder(JoinOp.INNER, this, other)

fun FromElement.leftJoin(other: FromElement) = JoinConditionBuilder(JoinOp.LEFT, this, other)

fun FromElement.rightJoin(other: FromElement) = JoinConditionBuilder(JoinOp.RIGHT, this, other)

fun FromElement.fullJoin(other: FromElement) = JoinConditionBuilder(JoinOp.FULL, this, other)


class JoinConditionBuilder(private val op: JoinOp, private val left: FromElement, private val right: FromElement) {

	fun on(condition: Expr<Boolean>) = JoinElement(op, left, right, OnJoinCondition(condition))

	fun using(vararg columns: Expr<*>) = JoinElement(op, left, right, UsingJoinCondition(columns.toList()))

}