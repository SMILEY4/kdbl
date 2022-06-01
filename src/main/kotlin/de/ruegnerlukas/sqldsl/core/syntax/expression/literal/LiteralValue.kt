package de.ruegnerlukas.sqldsl.core.syntax.expression.literal

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression

/**
 * An expression that represents a fixed value
 */
interface LiteralValue<T> : Expression<T>


/**
 * A fixed integer value
 */
class IntLiteralValue(val value: Int) : LiteralValue<Int>


/**
 * A fixed string value
 */
class StringLiteralValue(val value: String) : LiteralValue<String>


/**
 * A fixed boolean value
 */
class BooleanLiteralValue(val value: Boolean) : LiteralValue<Boolean>


/**
 * NULL
 */
class NullLiteralValue : LiteralValue<Any>


/**
 * the current timestamp
 */
class CurrentTimestampLiteralValue : LiteralValue<Int>


/**
 * A fixed list of other literals
 */
class ListLiteralValue<T>(val literals: List<LiteralValue<T>>) : LiteralValue<Any>
