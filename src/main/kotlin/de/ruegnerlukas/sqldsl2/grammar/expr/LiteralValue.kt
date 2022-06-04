package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement


interface LiteralValue : Expr


/**
 * Numeric values
 */
interface NumericLiteral : LiteralValue


/**
 * integer values
 */
class IntLiteral(val value: Int) : NumericLiteral


/**
 * floating-point values
 */
class FloatLiteral(val value: Float) : NumericLiteral


/**
 * String values
 */
class StringLiteral(val value: String) : LiteralValue


/**
 * TRUE or FALSE
 */
class BooleanLiteral(val value: Boolean) : LiteralValue


/**
 * List of other literal values
 */
class ListLiteral(val values: List<LiteralValue>) : LiteralValue


/**
 * NULL
 */
class NullLiteral : LiteralValue


/**
 * a sub-query
 */
class SubQueryLiteral(val query: QueryStatement) : LiteralValue


/**
 * predefined constants, e.g. CURRENT_TIMESTAMP, CURRENT_TIME, ...
 */
class MacroLiteral(val value: Value) : LiteralValue {
	companion object {
		enum class Value {
			CURRENT_TIMESTAMP
		}
	}
}