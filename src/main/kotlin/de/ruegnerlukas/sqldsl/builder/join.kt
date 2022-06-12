package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.JoinElement
import de.ruegnerlukas.sqldsl.dsl.statements.JoinOp
import de.ruegnerlukas.sqldsl.dsl.statements.OnJoinCondition
import de.ruegnerlukas.sqldsl.dsl.statements.UsingJoinCondition

fun FromElement.join(other: FromElement) = JoinConditionBuilder(JoinOp.LEFT, this, other)


class JoinConditionBuilder(private val op: JoinOp, private val left: FromElement, private val right: FromElement) {

	fun on(condition: Expr<Boolean>) = JoinElement(op, left, right, OnJoinCondition(condition))

	fun using(vararg columns: Expr<*>) = JoinElement(op, left, right, UsingJoinCondition(columns.toList()))

}