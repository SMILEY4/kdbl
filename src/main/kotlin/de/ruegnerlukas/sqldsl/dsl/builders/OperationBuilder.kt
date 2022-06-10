package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.FloatValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.IntValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType


fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.add(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddOperation(this, other)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.add(other: Int) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(other))
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.add(other: Float) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(other))

fun <T : NumericValueType> addAll(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddAllOperation(expressions)
fun <T : NumericValueType> addAll(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddAllOperation(expressions.toList())

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.sub(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation(this, other)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.sub(other: Int) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(other))
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.sub(other: Float) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(other))

fun Int.sub(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation(de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(this), other)
fun Float.sub(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation(de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(this), other)

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.mul(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.MulOperation(this, other)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.mul(other: Int) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.MulOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(other))
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.mul(other: Float) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.MulOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(other))

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.div(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation(this, other)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.div(other: Int) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(other))
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.div(other: Float) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation(this, de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(other))

fun Int.div(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation(de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(this), other)
fun Float.div(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation(de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(this), other)