package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.AndChainExpr
import de.ruegnerlukas.kdbl.dsl.expression.AndExpr
import de.ruegnerlukas.kdbl.dsl.expression.BetweenExpr
import de.ruegnerlukas.kdbl.dsl.expression.BooleanLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.DateLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.EqualExpr
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.GreaterEqualThanExpr
import de.ruegnerlukas.kdbl.dsl.expression.GreaterThanExpr
import de.ruegnerlukas.kdbl.dsl.expression.InListExpr
import de.ruegnerlukas.kdbl.dsl.expression.InQueryExpr
import de.ruegnerlukas.kdbl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.LessEqualThanExpr
import de.ruegnerlukas.kdbl.dsl.expression.LessThanExpr
import de.ruegnerlukas.kdbl.dsl.expression.LikeExpr
import de.ruegnerlukas.kdbl.dsl.expression.ListLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.NotEqualExpr
import de.ruegnerlukas.kdbl.dsl.expression.NotExpr
import de.ruegnerlukas.kdbl.dsl.expression.NotInListExpr
import de.ruegnerlukas.kdbl.dsl.expression.NotInQueryExpr
import de.ruegnerlukas.kdbl.dsl.expression.NotNullExpr
import de.ruegnerlukas.kdbl.dsl.expression.NullExpr
import de.ruegnerlukas.kdbl.dsl.expression.OrChainExpr
import de.ruegnerlukas.kdbl.dsl.expression.OrExpr
import de.ruegnerlukas.kdbl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.StringLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.TimeLiteralExpr
import de.ruegnerlukas.kdbl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.QueryStatement
import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime

fun <T> Expr<T>.isEqual(other: Expr<T>) = EqualExpr(this, other)
fun Expr<Boolean>.isEqual(other: Boolean) = EqualExpr(this, BooleanLiteralExpr(other))
fun Expr<Short>.isEqual(other: Short) = EqualExpr(this, ShortLiteralExpr(other))
fun Expr<Int>.isEqual(other: Int) = EqualExpr(this, IntLiteralExpr(other))
fun Expr<Long>.isEqual(other: Long) = EqualExpr(this, LongLiteralExpr(other))
fun Expr<Float>.isEqual(other: Float) = EqualExpr(this, FloatLiteralExpr(other))
fun Expr<Double>.isEqual(other: Double) = EqualExpr(this, DoubleLiteralExpr(other))
fun Expr<String>.isEqual(other: String) = EqualExpr(this, StringLiteralExpr(other))
fun Expr<SqlDate>.isEqual(other: SqlDate) = EqualExpr(this, DateLiteralExpr(other))
fun Expr<SqlTime>.isEqual(other: SqlTime) = EqualExpr(this, TimeLiteralExpr(other))


fun <T> Expr<T>.isNotEqual(other: Expr<T>) = NotEqualExpr(this, other)
fun Expr<Boolean>.isNotEqual(other: Boolean) = NotEqualExpr(this, BooleanLiteralExpr(other))
fun Expr<Short>.isNotEqual(other: Short) = NotEqualExpr(this, ShortLiteralExpr(other))
fun Expr<Int>.isNotEqual(other: Int) = NotEqualExpr(this, IntLiteralExpr(other))
fun Expr<Long>.isNotEqual(other: Long) = NotEqualExpr(this, LongLiteralExpr(other))
fun Expr<Float>.isNotEqual(other: Float) = NotEqualExpr(this, FloatLiteralExpr(other))
fun Expr<Double>.isNotEqual(other: Double) = NotEqualExpr(this, DoubleLiteralExpr(other))
fun Expr<String>.isNotEqual(other: String) = NotEqualExpr(this, StringLiteralExpr(other))
fun Expr<SqlDate>.isNotEqual(other: SqlDate) = NotEqualExpr(this, DateLiteralExpr(other))
fun Expr<SqlTime>.isNotEqual(other: SqlTime) = NotEqualExpr(this, TimeLiteralExpr(other))

fun <T> Expr<T>.isLessThan(other: Expr<T>) = LessThanExpr(this, other)
fun Expr<Short>.isLessThan(other: Short) = LessThanExpr(this, ShortLiteralExpr(other))
fun Expr<Int>.isLessThan(other: Int) = LessThanExpr(this, IntLiteralExpr(other))
fun Expr<Long>.isLessThan(other: Long) = LessThanExpr(this, LongLiteralExpr(other))
fun Expr<Float>.isLessThan(other: Float) = LessThanExpr(this, FloatLiteralExpr(other))
fun Expr<Double>.isLessThan(other: Double) = LessThanExpr(this, DoubleLiteralExpr(other))
fun Expr<SqlDate>.isLessThan(other: SqlDate) = LessThanExpr(this, DateLiteralExpr(other))
fun Expr<SqlTime>.isLessThan(other: SqlTime) = LessThanExpr(this, TimeLiteralExpr(other))

fun <T> Expr<T>.isLessOrEqualThan(other: Expr<T>) = LessEqualThanExpr(this, other)
fun Expr<Short>.isLessOrEqualThan(other: Short) = LessEqualThanExpr(this, ShortLiteralExpr(other))
fun Expr<Int>.isLessOrEqualThan(other: Int) = LessEqualThanExpr(this, IntLiteralExpr(other))
fun Expr<Long>.isLessOrEqualThan(other: Long) = LessEqualThanExpr(this, LongLiteralExpr(other))
fun Expr<Float>.isLessOrEqualThan(other: Float) = LessEqualThanExpr(this, FloatLiteralExpr(other))
fun Expr<Double>.isLessOrEqualThan(other: Double) = LessEqualThanExpr(this, DoubleLiteralExpr(other))
fun Expr<SqlDate>.isLessOrEqualThan(other: SqlDate) = LessEqualThanExpr(this, DateLiteralExpr(other))
fun Expr<SqlTime>.isLessOrEqualThan(other: SqlTime) = LessEqualThanExpr(this, TimeLiteralExpr(other))

fun <T> Expr<T>.isGreaterThan(other: Expr<T>) = GreaterThanExpr(this, other)
fun Expr<Short>.isGreaterThan(other: Short) = GreaterThanExpr(this, ShortLiteralExpr(other))
fun Expr<Int>.isGreaterThan(other: Int) = GreaterThanExpr(this, IntLiteralExpr(other))
fun Expr<Long>.isGreaterThan(other: Long) = GreaterThanExpr(this, LongLiteralExpr(other))
fun Expr<Float>.isGreaterThan(other: Float) = GreaterThanExpr(this, FloatLiteralExpr(other))
fun Expr<Double>.isGreaterThan(other: Double) = GreaterThanExpr(this, DoubleLiteralExpr(other))
fun Expr<SqlDate>.isGreaterThan(other: SqlDate) = GreaterThanExpr(this, DateLiteralExpr(other))
fun Expr<SqlTime>.isGreaterThan(other: SqlTime) = GreaterThanExpr(this, TimeLiteralExpr(other))

fun <T> Expr<T>.isGreaterOrEqualThan(other: Expr<T>) = GreaterEqualThanExpr(this, other)
fun Expr<Short>.isGreaterOrEqualThan(other: Short) = GreaterEqualThanExpr(this, ShortLiteralExpr(other))
fun Expr<Int>.isGreaterOrEqualThan(other: Int) = GreaterEqualThanExpr(this, IntLiteralExpr(other))
fun Expr<Long>.isGreaterOrEqualThan(other: Long) = GreaterEqualThanExpr(this, LongLiteralExpr(other))
fun Expr<Float>.isGreaterOrEqualThan(other: Float) = GreaterEqualThanExpr(this, FloatLiteralExpr(other))
fun Expr<Double>.isGreaterOrEqualThan(other: Double) = GreaterEqualThanExpr(this, DoubleLiteralExpr(other))
fun Expr<SqlDate>.isGreaterOrEqualThan(other: SqlDate) = GreaterEqualThanExpr(this, DateLiteralExpr(other))
fun Expr<SqlTime>.isGreaterOrEqualThan(other: SqlTime) = GreaterEqualThanExpr(this, TimeLiteralExpr(other))

fun Expr<*>.isNotNull() = NotNullExpr(this)
fun Expr<*>.isNull() = NullExpr(this)

fun <T> not(expr: Expr<T>) = NotExpr(expr)
infix fun Expr<Boolean>.and(other: Expr<Boolean>) = AndExpr(this, other)
infix fun Expr<Boolean>.or(other: Expr<Boolean>) = OrExpr(this, other)

fun and(vararg expr: Expr<Boolean>) = AndChainExpr(expr.toList())
fun and(expr: List<Expr<Boolean>>) = AndChainExpr(expr)

fun or(vararg expr: Expr<Boolean>) = OrChainExpr(expr.toList())
fun or(expr: List<Expr<Boolean>>) = OrChainExpr(expr)

fun Expr<String>.isLike(pattern: String) = LikeExpr(this, pattern)
fun <T> Expr<T>.isBetween(lower: Expr<T>, upper: Expr<T>) = BetweenExpr(this, lower, upper)

fun <T> Expr<T>.isIn(query: QueryStatement<T>) = InQueryExpr(this, query)
fun <T> Expr<T>.isIn(query: QueryBuilderEndStep<T>) = InQueryExpr(this, query.build<T>())
fun <T> Expr<T>.isIn(list: ListLiteralExpr<T>) = InListExpr(this, list)
fun <T> Expr<T>.isIn(values: List<Expr<T>>) = InListExpr(this, ListLiteralExpr(values))
fun <T> Expr<T>.isIn(vararg values: Expr<T>) = InListExpr(this, ListLiteralExpr(values.toList()))
fun Expr<Short>.isIn(vararg values: Short) = InListExpr(this, ListLiteralExpr(values.toList().map { ShortLiteralExpr(it) }))
fun Expr<Int>.isIn(vararg values: Int) = InListExpr(this, ListLiteralExpr(values.toList().map { IntLiteralExpr(it) }))
fun Expr<Long>.isIn(vararg values: Long) = InListExpr(this, ListLiteralExpr(values.toList().map { LongLiteralExpr(it) }))
fun Expr<Float>.isIn(vararg values: Float) = InListExpr(this, ListLiteralExpr(values.toList().map { FloatLiteralExpr(it) }))
fun Expr<String>.isIn(vararg values: String) = InListExpr(this, ListLiteralExpr(values.toList().map { StringLiteralExpr(it) }))
fun Expr<SqlDate>.isIn(vararg values: SqlDate) = InListExpr(this, ListLiteralExpr(values.toList().map { DateLiteralExpr(it) }))
fun Expr<SqlTime>.isIn(vararg values: SqlTime) = InListExpr(this, ListLiteralExpr(values.toList().map { TimeLiteralExpr(it) }))


fun <T> Expr<T>.isNotIn(query: QueryStatement<T>) = NotInQueryExpr(this, query)
fun <T> Expr<T>.isNotIn(query: QueryBuilderEndStep<T>) = NotInQueryExpr(this, query.build<T>())
fun <T> Expr<T>.isNotIn(list: ListLiteralExpr<T>) = NotInListExpr(this, list)
fun <T> Expr<T>.isNotIn(values: List<Expr<T>>) = NotInListExpr(this, ListLiteralExpr(values))
fun <T> Expr<T>.isNotIn(vararg values: Expr<T>) = NotInListExpr(this, ListLiteralExpr(values.toList()))
fun Expr<Short>.isNotIn(vararg values: Short) = NotInListExpr(this, ListLiteralExpr(values.toList().map { ShortLiteralExpr(it) }))
fun Expr<Int>.isNotIn(vararg values: Int) = NotInListExpr(this, ListLiteralExpr(values.toList().map { IntLiteralExpr(it) }))
fun Expr<Long>.isNotIn(vararg values: Long) = NotInListExpr(this, ListLiteralExpr(values.toList().map { LongLiteralExpr(it) }))
fun Expr<Float>.isNotIn(vararg values: Float) = NotInListExpr(this, ListLiteralExpr(values.toList().map { FloatLiteralExpr(it) }))
fun Expr<String>.isNotIn(vararg values: String) = NotInListExpr(this, ListLiteralExpr(values.toList().map { StringLiteralExpr(it) }))
fun Expr<SqlDate>.isNotIn(vararg values: SqlDate) = NotInListExpr(this, ListLiteralExpr(values.toList().map { DateLiteralExpr(it) }))
fun Expr<SqlTime>.isNotIn(vararg values: SqlTime) = NotInListExpr(this, ListLiteralExpr(values.toList().map { TimeLiteralExpr(it) }))
