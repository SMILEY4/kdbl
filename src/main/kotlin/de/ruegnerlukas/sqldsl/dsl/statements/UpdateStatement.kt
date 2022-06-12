package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.BlobLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.DateLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.DoubleLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LongLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.ShortLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.expr.TimeLiteralExpr
import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

class UpdateStatement(
	val onConflict: OnConflict,
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
fun Column<ByteArray>.set(value: ByteArray) = UpdateElement(this, BlobLiteralExpr(value))
fun Column<SqlDate>.set(value: SqlDate) = UpdateElement(this, DateLiteralExpr(value))
fun Column<SqlTime>.set(value: SqlTime) = UpdateElement(this, TimeLiteralExpr(value))
