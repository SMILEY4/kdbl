package de.ruegnerlukas.sqldsl.dsl.grammar.expr

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.BooleanValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.FloatValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.IntValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NoValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.StringValueType


interface LiteralValue<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>


/**
 * Numeric values
 */
interface NumericLiteral<T: NumericValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<T>


/**
 * integer values
 */
class IntLiteral(val value: Int) : de.ruegnerlukas.sqldsl.dsl.grammar.expr.NumericLiteral<IntValueType>


/**
 * floating-point values
 */
class FloatLiteral(val value: Float) : de.ruegnerlukas.sqldsl.dsl.grammar.expr.NumericLiteral<FloatValueType>


/**
 * String values
 */
class StringLiteral(val value: String) : de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<StringValueType>


/**
 * TRUE or FALSE
 */
class BooleanLiteral(val value: Boolean) : de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<BooleanValueType>


/**
 * List of other literal values
 */
class ListLiteral<T: AnyValueType>(val values: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<T>>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<T>


/**
 * NULL
 */
class NullLiteral : de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<NoValueType>


/**
 * a sub-query
 */
interface SubQueryLiteral<T: AnyValueType>: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<T>


/**
 * predefined constants, e.g. CURRENT_TIMESTAMP, CURRENT_TIME, ...
 */
class MacroLiteral(val value: de.ruegnerlukas.sqldsl.dsl.grammar.expr.MacroLiteral.Companion.Value) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<AnyValueType> {
	companion object {
		enum class Value {
			CURRENT_TIMESTAMP
		}
	}
}