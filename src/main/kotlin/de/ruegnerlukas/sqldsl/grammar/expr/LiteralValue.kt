package de.ruegnerlukas.sqldsl.grammar.expr

import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.BooleanValueType
import de.ruegnerlukas.sqldsl.schema.FloatValueType
import de.ruegnerlukas.sqldsl.schema.IntValueType
import de.ruegnerlukas.sqldsl.schema.NoValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType
import de.ruegnerlukas.sqldsl.schema.StringValueType


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
class SubQueryLiteral<T: AnyValueType>(val query: QueryStatement): LiteralValue<T>


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