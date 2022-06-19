package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.AddAllExpr
import de.ruegnerlukas.kdbl.dsl.expression.AddExpr
import de.ruegnerlukas.kdbl.dsl.expression.DivExpr
import de.ruegnerlukas.kdbl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.MulExpr
import de.ruegnerlukas.kdbl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.SubExpr

fun <T> Expr<T>.add(other: Expr<T>) = AddExpr(this, other)
fun <T> Expr<T>.sub(other: Expr<T>) = SubExpr(this, other)
fun <T> Expr<T>.mul(other: Expr<T>) = MulExpr(this, other)
fun <T> Expr<T>.div(other: Expr<T>) = DivExpr(this, other)

fun Expr<Short>.add(other: Short) = AddExpr(this, ShortLiteralExpr(other))
fun Expr<Short>.sub(other: Short) = SubExpr(this, ShortLiteralExpr(other))
fun Expr<Short>.mul(other: Short) = MulExpr(this, ShortLiteralExpr(other))
fun Expr<Short>.div(other: Short) = DivExpr(this, ShortLiteralExpr(other))

fun Expr<Int>.add(other: Int) = AddExpr(this, IntLiteralExpr(other))
fun Expr<Int>.sub(other: Int) = SubExpr(this, IntLiteralExpr(other))
fun Expr<Int>.mul(other: Int) = MulExpr(this, IntLiteralExpr(other))
fun Expr<Int>.div(other: Int) = DivExpr(this, IntLiteralExpr(other))

fun Expr<Long>.add(other: Long) = AddExpr(this, LongLiteralExpr(other))
fun Expr<Long>.sub(other: Long) = SubExpr(this, LongLiteralExpr(other))
fun Expr<Long>.mul(other: Long) = MulExpr(this, LongLiteralExpr(other))
fun Expr<Long>.div(other: Long) = DivExpr(this, LongLiteralExpr(other))

fun Expr<Float>.add(other: Float) = AddExpr(this, FloatLiteralExpr(other))
fun Expr<Float>.sub(other: Float) = SubExpr(this, FloatLiteralExpr(other))
fun Expr<Float>.mul(other: Float) = MulExpr(this, FloatLiteralExpr(other))
fun Expr<Float>.div(other: Float) = DivExpr(this, FloatLiteralExpr(other))

fun Expr<Double>.add(other: Double) = AddExpr(this, DoubleLiteralExpr(other))
fun Expr<Double>.sub(other: Double) = SubExpr(this, DoubleLiteralExpr(other))
fun Expr<Double>.mul(other: Double) = MulExpr(this, DoubleLiteralExpr(other))
fun Expr<Double>.div(other: Double) = DivExpr(this, DoubleLiteralExpr(other))

fun <T> addAll(expr: List<Expr<T>>) = AddAllExpr(expr)
fun <T> addAll(vararg expr: Expr<T>) = AddAllExpr(expr.toList())
