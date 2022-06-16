package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Column
import de.ruegnerlukas.sqldsl.dsl.expression.DateLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Returning
import de.ruegnerlukas.sqldsl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Table
import de.ruegnerlukas.sqldsl.dsl.expression.TimeLiteralExpr
import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

class UpdateStatement(
	val target: Table,
	val set: List<UpdateElement<*>>,
	val where: Expr<Boolean>? = null,
	val from: FromStatement? = null,
	val returning: Returning? = null,
)


interface UpdateBuilderEndStep {
	fun build(): UpdateStatement
}


class UpdateElement<T>(
	val column: Column<T>,
	val value: Expr<T>
)


fun <T> Column<T>.set(expr: Expr<T>) = UpdateElement(this, expr)

fun Column<Boolean>.set(value: Boolean) = UpdateElement(this, BooleanLiteralExpr(value))
fun Column<Short>.set(value: Short) = UpdateElement(this, ShortLiteralExpr(value))
fun Column<Int>.set(value: Int) = UpdateElement(this, IntLiteralExpr(value))
fun Column<Long>.set(value: Long) = UpdateElement(this, LongLiteralExpr(value))
fun Column<Float>.set(value: Float) = UpdateElement(this, FloatLiteralExpr(value))
fun Column<Double>.set(value: Double) = UpdateElement(this, DoubleLiteralExpr(value))
fun Column<String>.set(value: String) = UpdateElement(this, StringLiteralExpr(value))
fun Column<SqlDate>.set(value: SqlDate) = UpdateElement(this, DateLiteralExpr(value))
fun Column<SqlTime>.set(value: SqlTime) = UpdateElement(this, TimeLiteralExpr(value))
