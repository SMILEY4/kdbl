package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Expr

class FromStatement(val elements: List<FromElement>)

interface FromElement

class JoinElement(val op: JoinOp, val left: FromElement, val right: FromElement, val condition: JoinCondition) : FromElement

interface JoinCondition

class OnJoinCondition(val condition: Expr<Boolean>) : JoinCondition

class UsingJoinCondition(val columns: List<Expr<*>>) : JoinCondition

enum class JoinOp {
	INNER,
	LEFT,
	RIGHT,
	FULL,
}