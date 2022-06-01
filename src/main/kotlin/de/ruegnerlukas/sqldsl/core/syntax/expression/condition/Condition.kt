package de.ruegnerlukas.sqldsl.core.syntax.expression.condition

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression

/**
 * An expression that evaluates to true or false
 */
interface Condition : Expression<Boolean>


/**
 * Invert the given condition
 */
class NotCondition(val condition: Condition) : Condition


/**
 * Whether the given expression is "NULL"
 */
class IsNullCondition(val expression: Expression<*>) : Condition


/**
 * Whether the given expression is not "NULL"
 */
class IsNotNullCondition(val expression: Expression<*>) : Condition


/**
 * Whether the given conditions are all true
 */
class AndCondition(val left: Condition, val right: Condition) : Condition


/**
 * Whether one of the given conditions is true
 */
class OrCondition(val left: Condition, val right: Condition) : Condition


/**
 * Whether the given expression matches the given pattern
 */
class LikeCondition(val expression: Expression<String>, val pattern: String) : Condition


/**
 * Whether the given values is between two values defined by the other two expressions
 */
class BetweenCondition(val expression: Expression<Int>, val min: Expression<Int>, val max: Expression<Int>) : Condition


/**
 * Whether the two given expressions are equal
 */
class EqualCondition<T>(val left: Expression<T>, val right: Expression<T>) : Condition


/**
 * Whether the first expressions is less than the second
 */
class LessThanCondition(val left: Expression<Int>, val right: Expression<Int>) : Condition


/**
 * Whether the first expressions is greater than the second
 */
class GreaterThanCondition(val left: Expression<Int>, val right: Expression<Int>) : Condition