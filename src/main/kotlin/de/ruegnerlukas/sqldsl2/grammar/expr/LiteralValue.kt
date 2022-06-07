package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.schema.AnyValueType
import de.ruegnerlukas.sqldsl2.schema.BooleanValueType
import de.ruegnerlukas.sqldsl2.schema.FloatValueType
import de.ruegnerlukas.sqldsl2.schema.IntValueType
import de.ruegnerlukas.sqldsl2.schema.NoValueType
import de.ruegnerlukas.sqldsl2.schema.NumericValueType
import de.ruegnerlukas.sqldsl2.schema.StringValueType


interface LiteralValue<T: AnyValueType> : Expr<T>


/**
 * Numeric values
 */
interface NumericLiteral<T: NumericValueType> : LiteralValue<T>


/**
 * integer values
 */
class IntLiteral(val value: Int) : NumericLiteral<IntValueType>


/**
 * floating-point values
 */
class FloatLiteral(val value: Float) : NumericLiteral<FloatValueType>


/**
 * String values
 */
class StringLiteral(val value: String) : LiteralValue<StringValueType>


/**
 * TRUE or FALSE
 */
class BooleanLiteral(val value: Boolean) : LiteralValue<BooleanValueType>


/**
 * List of other literal values
 */
class ListLiteral<T: AnyValueType>(val values: List<LiteralValue<T>>) : LiteralValue<T>


/**
 * NULL
 */
class NullLiteral : LiteralValue<NoValueType>


/**
 * a sub-query
 */
interface SubQueryLiteral : LiteralValue<AnyValueType>


/**
 * predefined constants, e.g. CURRENT_TIMESTAMP, CURRENT_TIME, ...
 */
class MacroLiteral(val value: Value) : LiteralValue<AnyValueType> {
	companion object {
		enum class Value {
			CURRENT_TIMESTAMP
		}
	}
}