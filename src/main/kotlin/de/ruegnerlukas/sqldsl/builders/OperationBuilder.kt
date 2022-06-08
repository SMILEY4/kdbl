package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.expr.AddAllOperation
import de.ruegnerlukas.sqldsl.grammar.expr.AddOperation
import de.ruegnerlukas.sqldsl.grammar.expr.DivOperation
import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.grammar.expr.FloatLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.MulOperation
import de.ruegnerlukas.sqldsl.grammar.expr.SubOperation
import de.ruegnerlukas.sqldsl.schema.FloatValueType
import de.ruegnerlukas.sqldsl.schema.IntValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType


fun <T : NumericValueType> Expr<T>.add(other: Expr<T>) = AddOperation(this, other)
fun Expr<IntValueType>.add(other: Int) = AddOperation(this, IntLiteral(other))
fun Expr<FloatValueType>.add(other: Float) = AddOperation(this, FloatLiteral(other))

fun <T : NumericValueType> addAll(expressions: List<Expr<T>>) = AddAllOperation(expressions)
fun <T : NumericValueType> addAll(vararg expressions: Expr<T>) = AddAllOperation(expressions.toList())

fun <T : NumericValueType> Expr<T>.sub(other: Expr<T>) = SubOperation(this, other)
fun Expr<IntValueType>.sub(other: Int) = SubOperation(this, IntLiteral(other))
fun Expr<FloatValueType>.sub(other: Float) = SubOperation(this, FloatLiteral(other))

fun Int.sub(other: Expr<IntValueType>) = SubOperation(IntLiteral(this), other)
fun Float.sub(other: Expr<FloatValueType>) = SubOperation(FloatLiteral(this), other)

fun <T : NumericValueType> Expr<T>.mul(other: Expr<T>) = MulOperation(this, other)
fun Expr<IntValueType>.mul(other: Int) = MulOperation(this, IntLiteral(other))
fun Expr<FloatValueType>.mul(other: Float) = MulOperation(this, FloatLiteral(other))

fun <T : NumericValueType> Expr<T>.div(other: Expr<T>) = DivOperation(this, other)
fun Expr<IntValueType>.div(other: Int) = DivOperation(this, IntLiteral(other))
fun Expr<FloatValueType>.div(other: Float) = DivOperation(this, FloatLiteral(other))

fun Int.div(other: Expr<IntValueType>) = DivOperation(IntLiteral(this), other)
fun Float.div(other: Expr<FloatValueType>) = DivOperation(FloatLiteral(this), other)