package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.AliasExpr
import de.ruegnerlukas.kdbl.dsl.expression.DerivedTable
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.PlaceholderLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.expression.TypecastExpr
import de.ruegnerlukas.kdbl.dsl.statements.FromElement
import de.ruegnerlukas.kdbl.dsl.statements.SelectAllElement
import de.ruegnerlukas.kdbl.dsl.statements.SelectAllFromTableElement

fun <T> Expr<T>.alias(alias: String) = AliasExpr(this, alias)
fun <T> Expr<T>.alias(aliasExpr: AliasExpr<T>) = aliasExpr.assign(this)

fun FromElement.assign(table: DerivedTable): DerivedTable {
	table.assign(this)
	return table
}

fun Table.allColumns() = SelectAllFromTableElement(this)
fun allColumns() = SelectAllElement()

fun <T> placeholder(name: String) = PlaceholderLiteralExpr<T>(name)

@JvmName("sCastInt") fun Expr<Short>.int() = TypecastExpr<Short, Int>(this)
@JvmName("sCastLong") fun Expr<Short>.long() = TypecastExpr<Short, Long>(this)
@JvmName("sCastFloat") fun Expr<Short>.float() = TypecastExpr<Short, Float>(this)
@JvmName("sCastDouble") fun Expr<Short>.double() = TypecastExpr<Short, Double>(this)

@JvmName("iCastShort") fun Expr<Int>.short() = TypecastExpr<Int, Short>(this)
@JvmName("iCastLong") fun Expr<Int>.long() = TypecastExpr<Int, Long>(this)
@JvmName("iCastFloat") fun Expr<Int>.float() = TypecastExpr<Int, Float>(this)
@JvmName("iCastDouble") fun Expr<Int>.double() = TypecastExpr<Int, Double>(this)

@JvmName("lCastShort") fun Expr<Long>.short() = TypecastExpr<Long, Short>(this)
@JvmName("lCastInt") fun Expr<Long>.int() = TypecastExpr<Long, Int>(this)
@JvmName("lCastFloat") fun Expr<Long>.float() = TypecastExpr<Long, Float>(this)
@JvmName("lCastDouble") fun Expr<Long>.double() = TypecastExpr<Long, Double>(this)

@JvmName("fCastShort") fun Expr<Float>.short() = TypecastExpr<Float, Short>(this)
@JvmName("fCastInt") fun Expr<Float>.int() = TypecastExpr<Float, Int>(this)
@JvmName("fCastLong") fun Expr<Float>.long() = TypecastExpr<Float, Long>(this)
@JvmName("fCastDouble") fun Expr<Float>.double() = TypecastExpr<Float, Double>(this)

@JvmName("dCastShort") fun Expr<Double>.short() = TypecastExpr<Double, Short>(this)
@JvmName("dCastInt") fun Expr<Double>.int() = TypecastExpr<Double, Int>(this)
@JvmName("dCastLong") fun Expr<Double>.long() = TypecastExpr<Double, Long>(this)
@JvmName("dCastFloat") fun Expr<Double>.float() = TypecastExpr<Double, Float>(this)

@JvmName("anyCastShort") fun Expr<Any>.short() = TypecastExpr<Any, Short>(this)
@JvmName("anyCastInt") fun Expr<Any>.int() = TypecastExpr<Any, Int>(this)
@JvmName("anyCastLong") fun Expr<Any>.long() = TypecastExpr<Any, Long>(this)
@JvmName("anyCastFloat") fun Expr<Any>.float() = TypecastExpr<Any, Float>(this)
@JvmName("anyCastString") fun Expr<Any>.string() = TypecastExpr<Any, String>(this)
@JvmName("anyCastBoolean") fun Expr<Any>.bool() = TypecastExpr<Any, Boolean>(this)