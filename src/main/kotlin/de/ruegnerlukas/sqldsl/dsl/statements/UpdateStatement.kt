package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Table

class UpdateStatement(
	val onConflict: OnConflict,
	val target: Table,
	val set: List<UpdateElement<*>>,
	val where: Expr<Boolean>?,
	val from: FromStatement?,
	val returning: Returning?
)

class UpdateElement<T>(
	val column: Column<T>,
	val value: Expr<T>
)

fun <T> Column<T>.set(expr: Expr<T>) = UpdateElement(this, expr)
fun Column<Int>.set(value: Int) = UpdateElement(this, IntLiteralExpr(value))
fun Column<Float>.set(value: Float) = UpdateElement(this, FloatLiteralExpr(value))
fun Column<String>.set(value: String) = UpdateElement(this, StringLiteralExpr(value))
fun Column<Boolean>.set(value: Boolean) = UpdateElement(this, BooleanLiteralExpr(value))