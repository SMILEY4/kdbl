package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.AddAllExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AddExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AliasExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AndChainExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AndExpr
import de.ruegnerlukas.sqldsl.dsl.expr.BetweenExpr
import de.ruegnerlukas.sqldsl.dsl.expr.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountAllDistinctExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountAllExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountDistinctExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountExpr
import de.ruegnerlukas.sqldsl.dsl.expr.DerivedTable
import de.ruegnerlukas.sqldsl.dsl.expr.DivExpr
import de.ruegnerlukas.sqldsl.dsl.expr.EqualExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.GreaterEqualThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.GreaterThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.InListExpr
import de.ruegnerlukas.sqldsl.dsl.expr.InQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expr.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LessEqualThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LessThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LikeExpr
import de.ruegnerlukas.sqldsl.dsl.expr.ListLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.MaxExpr
import de.ruegnerlukas.sqldsl.dsl.expr.MinExpr
import de.ruegnerlukas.sqldsl.dsl.expr.MulExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotEqualExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotInListExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotInQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotNullExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NullExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OrChainExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OrExpr
import de.ruegnerlukas.sqldsl.dsl.expr.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.SubExpr
import de.ruegnerlukas.sqldsl.dsl.expr.SumExpr
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement

fun <T> Expr<T>.isEqual(other: Expr<T>) = EqualExpr(this, other)
fun Expr<Int>.isEqual(other: Int) = EqualExpr(this, IntLiteralExpr(other))
fun Expr<Float>.isEqual(other: Float) = EqualExpr(this, FloatLiteralExpr(other))
fun Expr<Boolean>.isEqual(other: Boolean) = EqualExpr(this, BooleanLiteralExpr(other))
fun Expr<String>.isEqual(other: String) = EqualExpr(this, StringLiteralExpr(other))

fun <T> Expr<T>.isNotEqual(other: Expr<T>) = NotEqualExpr(this, other)
fun Expr<Int>.isNotEqual(other: Int) = NotEqualExpr(this, IntLiteralExpr(other))
fun Expr<Float>.isNotEqual(other: Float) = NotEqualExpr(this, FloatLiteralExpr(other))
fun Expr<Boolean>.isNotEqual(other: Boolean) = NotEqualExpr(this, BooleanLiteralExpr(other))
fun Expr<String>.isNotEqual(other: String) = NotEqualExpr(this, StringLiteralExpr(other))

fun <T> Expr<T>.isLessThan(other: Expr<T>) = LessThanExpr(this, other)
fun Expr<Int>.isLessThan(other: Int) = LessThanExpr(this, IntLiteralExpr(other))
fun Expr<Float>.isLessThan(other: Float) = LessThanExpr(this, FloatLiteralExpr(other))

fun <T> Expr<T>.isLessOrEqualThan(other: Expr<T>) = LessEqualThanExpr(this, other)
fun Expr<Int>.isLessOrEqualThan(other: Int) = LessEqualThanExpr(this, IntLiteralExpr(other))
fun Expr<Float>.isLessOrEqualThan(other: Float) = LessEqualThanExpr(this, FloatLiteralExpr(other))

fun <T> Expr<T>.isGreaterThan(other: Expr<T>) = GreaterThanExpr(this, other)
fun Expr<Int>.isGreaterThan(other: Int) = GreaterThanExpr(this, IntLiteralExpr(other))
fun Expr<Float>.isGreaterThan(other: Float) = GreaterThanExpr(this, FloatLiteralExpr(other))

fun <T> Expr<T>.isGreaterOrEqualThan(other: Expr<T>) = GreaterEqualThanExpr(this, other)
fun Expr<Int>.isGreaterOrEqualThan(other: Int) = GreaterEqualThanExpr(this, IntLiteralExpr(other))
fun Expr<Float>.isGreaterOrEqualThan(other: Float) = GreaterEqualThanExpr(this, FloatLiteralExpr(other))

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

fun Expr<Int>.isIn(vararg values: Int) = InListExpr(this, ListLiteralExpr(values.toList().map { IntLiteralExpr(it) }))
fun Expr<Float>.isIn(vararg values: Float) = InListExpr(this, ListLiteralExpr(values.toList().map { FloatLiteralExpr(it) }))
fun Expr<Boolean>.isIn(vararg values: Boolean) = InListExpr(this, ListLiteralExpr(values.toList().map { BooleanLiteralExpr(it) }))
fun Expr<String>.isIn(vararg values: String) = InListExpr(this, ListLiteralExpr(values.toList().map { StringLiteralExpr(it) }))

fun <T> Expr<T>.isNotIn(query: QueryStatement<T>) = NotInQueryExpr(this, query)
fun <T> Expr<T>.isNotIn(query: QueryBuilderEndStep<T>) = NotInQueryExpr(this, query.build<T>())
fun <T> Expr<T>.isNotIn(list: ListLiteralExpr<T>) = NotInListExpr(this, list)
fun <T> Expr<T>.isNotIn(values: List<Expr<T>>) = NotInListExpr(this, ListLiteralExpr(values))
fun <T> Expr<T>.isNotIn(vararg values: Expr<T>) = NotInListExpr(this, ListLiteralExpr(values.toList()))
fun Expr<Int>.isNotIn(vararg values: Int) = NotInListExpr(this, ListLiteralExpr(values.toList().map { IntLiteralExpr(it) }))
fun Expr<Float>.isNotIn(vararg values: Float) = NotInListExpr(this, ListLiteralExpr(values.toList().map { FloatLiteralExpr(it) }))
fun Expr<Boolean>.isNotIn(vararg values: Boolean) = NotInListExpr(this, ListLiteralExpr(values.toList().map { BooleanLiteralExpr(it) }))
fun Expr<String>.isNotIn(vararg values: String) = NotInListExpr(this, ListLiteralExpr(values.toList().map { StringLiteralExpr(it) }))

fun <T> Expr<T>.add(other: Expr<T>) = AddExpr(this, other)
fun <T> Expr<T>.sub(other: Expr<T>) = SubExpr(this, other)
fun <T> Expr<T>.mul(other: Expr<T>) = MulExpr(this, other)
fun <T> Expr<T>.div(other: Expr<T>) = DivExpr(this, other)

fun Expr<Int>.add(other: Int) = AddExpr(this, IntLiteralExpr(other))
fun Expr<Int>.sub(other: Int) = SubExpr(this, IntLiteralExpr(other))
fun Expr<Int>.mul(other: Int) = MulExpr(this, IntLiteralExpr(other))
fun Expr<Int>.div(other: Int) = DivExpr(this, IntLiteralExpr(other))

fun Expr<Float>.add(other: Float) = AddExpr(this, FloatLiteralExpr(other))
fun Expr<Float>.sub(other: Float) = SubExpr(this, FloatLiteralExpr(other))
fun Expr<Float>.mul(other: Float) = MulExpr(this, FloatLiteralExpr(other))
fun Expr<Float>.div(other: Float) = DivExpr(this, FloatLiteralExpr(other))

fun <T> addAll(expr: List<Expr<T>>) = AddAllExpr(expr)
fun <T> addAll(vararg expr: Expr<T>) = AddAllExpr(expr.toList())

fun countAll() = CountAllExpr()
fun countAllDistinct() = CountAllDistinctExpr()
fun <T> Expr<T>.count() = CountExpr(this)
fun <T> Expr<T>.countDistinct() = CountDistinctExpr(this)
fun <T> Expr<T>.min() = MinExpr(this)
fun <T> Expr<T>.max() = MaxExpr(this)
fun <T> Expr<T>.sum() = SumExpr(this)

fun <T> Expr<T>.alias(alias: String) = AliasExpr(this, alias)
fun <T> Expr<T>.alias(aliasExpr: AliasExpr<T>) = aliasExpr.assign(this)

fun FromElement.assign(table: DerivedTable): DerivedTable {
	table.assign(this)
	return table
}