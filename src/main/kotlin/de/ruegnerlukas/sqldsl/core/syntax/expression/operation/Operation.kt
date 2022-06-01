package de.ruegnerlukas.sqldsl.core.syntax.expression.operation

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression

/**
 * An expression that represents an (arithmetic) operation that results in a new value
 */
interface Operation<T> : Expression<T>


/**
 * "left - right"
 */
class SubOperation(val left: Expression<Int>, val right: Expression<Int>) : Operation<Int>


/**
 * left + right
 */
class AddOperation(val left: Expression<Int>, val right: Expression<Int>) : Operation<Int>


/**
 * left * right
 */
class MulOperation(val left: Expression<Int>, val right: Expression<Int>) : Operation<Int>
