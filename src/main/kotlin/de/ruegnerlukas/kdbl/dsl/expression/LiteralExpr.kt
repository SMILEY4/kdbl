package de.ruegnerlukas.kdbl.dsl.expression

import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime

/**
 * A single constant value as an expression
 */
interface LiteralExpr<out T> : Expr<T>

class BooleanLiteralExpr(val value: Boolean) : LiteralExpr<Boolean>
class ShortLiteralExpr(val value: Short) : LiteralExpr<Short>
class IntLiteralExpr(val value: Int) : LiteralExpr<Int>
class LongLiteralExpr(val value: Long) : LiteralExpr<Long>
class FloatLiteralExpr(val value: Float) : LiteralExpr<Float>
class DoubleLiteralExpr(val value: Double) : LiteralExpr<Double>
class StringLiteralExpr(val value: String) : LiteralExpr<String>
class DateLiteralExpr(val value: SqlDate) : LiteralExpr<SqlDate>
class TimeLiteralExpr(val value: SqlTime) : LiteralExpr<SqlTime>
class ListLiteralExpr<T>(val values: List<Expr<T>>) : LiteralExpr<T>
class PlaceholderLiteralExpr<T>(val name: String) : LiteralExpr<T>
class NullLiteralExpr<T> : LiteralExpr<T>


