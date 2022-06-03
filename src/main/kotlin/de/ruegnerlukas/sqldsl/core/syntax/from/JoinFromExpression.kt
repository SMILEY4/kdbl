package de.ruegnerlukas.sqldsl.core.syntax.from

import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.Condition
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef

class JoinFromExpression(
	val left: FromExpression,
	val right: FromExpression,
	val joinOp: JoinOp,
	val constraint: JoinConstraint,
) : FromExpression

enum class JoinOp {
	LEFT,
	INNER,
	CROSS
}

interface JoinConstraint

class ConditionJoinConstraint(val condition: Condition) : JoinConstraint

class UsingJoinConstraint(val columns: List<ColumnRef<*, *>>) : JoinConstraint


fun FromExpression.joinLeft(other: FromExpression): JoinBuilder {
	return JoinBuilder(JoinOp.LEFT, this, other)
}

fun FromExpression.joinInner(other: FromExpression): JoinBuilder {
	return JoinBuilder(JoinOp.INNER, this, other)
}

fun FromExpression.joinCross(other: FromExpression): JoinBuilder {
	return JoinBuilder(JoinOp.CROSS, this, other)
}


class JoinBuilder(private val op: JoinOp, private val left: FromExpression, private val right: FromExpression) {

	fun using(columns: List<ColumnRef<*, *>>): JoinFromExpression {
		return JoinFromExpression(left, right, op, UsingJoinConstraint(columns))
	}

	fun on(condition: Condition): JoinFromExpression {
		return JoinFromExpression(left, right, op, ConditionJoinConstraint(condition))
	}

}

//fun joinLeft(block: JoinBuilder.() -> Unit): JoinFromExpression {
//	return JoinBuilder(JoinOp.LEFT).apply(block).build()
//}
//
//fun joinInner(block: JoinBuilder.() -> Unit) {
//	JoinBuilder(JoinOp.INNER).apply(block).build()
//}
//
//fun joinCross(block: JoinBuilder.() -> Unit) {
//	JoinBuilder(JoinOp.CROSS).apply(block).build()
//}
//
//class JoinBuilder(private val op: JoinOp) {
//
//	private var left: FromExpression? = null
//	private var right: FromExpression? = null
//	private var on: Condition? = null
//	private var using: List<ColumnRef<*, *>>? = null
//
//	fun left(table: TableRef) {
//		this.left = table
//	}
//
//	fun right(table: TableRef) {
//		this.right = table
//	}
//
//	fun on(condition: Condition) {
//		this.on = condition
//	}
//
//	fun using(vararg columns: ColumnRef<*, *>) {
//		this.using = columns.toList()
//	}
//
//	internal fun build(): JoinFromExpression {
//		if (on != null) {
//			return JoinFromExpression(left!!, right!!, op, ConditionJoinConstraint(on!!))
//		} else {
//			return JoinFromExpression(left!!, right!!, op, UsingJoinConstraint(using!!))
//		}
//	}
//}