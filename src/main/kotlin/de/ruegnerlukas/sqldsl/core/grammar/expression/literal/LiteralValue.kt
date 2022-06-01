package de.ruegnerlukas.sqldsl.core.grammar.expression.literal

import de.ruegnerlukas.sqldsl.core.grammar.expression.Expression

interface LiteralValue: Expression

class IntLiteralValue(val value: Int) : LiteralValue

class StringLiteralValue(val value: String) : LiteralValue

class BooleanLiteralValue(val value: Boolean) : LiteralValue

class NullLiteralValue : LiteralValue

class CurrentTimestampLiteralValue : LiteralValue

class ListLiteralValue(val literals: List<LiteralValue>) : LiteralValue


fun literal(value: Int): LiteralValue {
    return IntLiteralValue(value)
}

fun literal(value: String): LiteralValue {
    return StringLiteralValue(value)
}

fun literal(value: Boolean): LiteralValue {
    return BooleanLiteralValue(value)
}

fun literalNull(): LiteralValue {
    return NullLiteralValue()
}

fun literalCurrentTimestamp(): LiteralValue {
    return CurrentTimestampLiteralValue()
}

fun literalList(vararg literals: LiteralValue): LiteralValue {
    return ListLiteralValue(literals.toList())
}

fun literalList(literals: List<LiteralValue>): LiteralValue {
    return ListLiteralValue(literals)
}