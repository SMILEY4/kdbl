package de.ruegnerlukas.sqldsl.core.syntax.expression.operation

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.select.OperationSelectExpression

/**
 * An expression that represents an (arithmetic) operation that results in a new value
 */
interface Operation<T> : Expression<T>, OperationSelectExpression


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


/**
 * COUNT(*)
 */
class CountAllOperation() : Operation<Int>


/**
 * COUNT( DISTINCT column )
 */
class CountDistinctOperation(val column: ColumnRef<*, *>) : Operation<Int>
