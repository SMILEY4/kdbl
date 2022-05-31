package de.ruegnerlukas.sqldsl.core.grammar.expression

interface Condition : Expression

class NotCondition(val expression: Expression) : Condition

class IsNullCondition(val expression: Expression) : Condition

class AndCondition(val left: Expression, val right: Expression) : Condition

class OrCondition(val left: Expression, val right: Expression) : Condition

class LikeCondition(val expression: Expression, val pattern: String) : Condition

class BetweenCondition(val expression: Expression, val min: Expression, val max: Expression) : Condition


fun not(expression: Expression): NotCondition {
    return NotCondition(expression)
}

fun isNull(expression: Expression): IsNullCondition {
    return IsNullCondition(expression)
}

fun and(left: Expression, right: Expression): AndCondition {
    return AndCondition(left, right)
}

fun or(left: Expression, right: Expression): OrCondition {
    return OrCondition(left, right)
}

fun like(expression: Expression, pattern: String): LikeCondition {
    return LikeCondition(expression, pattern)
}

fun between(expression: Expression, min: Expression, max: Expression): BetweenCondition {
    return BetweenCondition(expression, min, max)
}


