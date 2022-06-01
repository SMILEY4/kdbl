package de.ruegnerlukas.sqldsl.core.syntax.expression.operation

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression

interface Operation: Expression

class SubOperation(val left: Expression, val right: Expression): Operation

class AddOperation(val left: Expression, val right: Expression): Operation

class MulOperation(val left: Expression, val right: Expression): Operation



fun sub(left: Expression, right: Expression): Operation {
    return SubOperation(left, right)
}

fun add(left: Expression, right: Expression): Operation {
    return AddOperation(left, right)
}

fun mul(left: Expression, right: Expression): Operation {
    return MulOperation(left, right)
}