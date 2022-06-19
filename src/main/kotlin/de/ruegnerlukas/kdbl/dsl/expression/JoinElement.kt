package de.ruegnerlukas.kdbl.dsl.expression

import de.ruegnerlukas.kdbl.dsl.statements.FromElement

/**
 * A join-clause
 */
class JoinElement(val op: JoinOp, val left: FromElement, val right: FromElement, val condition: JoinCondition) : FromElement


/**
 * The condition to join on
 */
interface JoinCondition


/**
 * use a (boolean) condition for the join
 */
class OnJoinCondition(val condition: Expr<Boolean>) : JoinCondition


/**
 * specify columns to join on
 */
class UsingJoinCondition(val columns: List<Expr<*>>) : JoinCondition


/**
 * The type of the join
 */
enum class JoinOp {
	INNER,
	LEFT,
	RIGHT,
	FULL,
}