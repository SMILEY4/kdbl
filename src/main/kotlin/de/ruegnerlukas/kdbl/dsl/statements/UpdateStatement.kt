package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.BooleanLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Column
import de.ruegnerlukas.kdbl.dsl.expression.DateLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Returning
import de.ruegnerlukas.kdbl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.StringLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.expression.TimeLiteralExpr
import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime

/**
 * Either the [UpdateStatement] directly or a builder that can produce the statement. For some situations, both can be used interchangeably
 */
sealed interface SqlUpdateStatement


/**
 * An "UPDATE-Statement
 */
class UpdateStatement(
	val target: Table,
	val set: List<UpdateElement<*>>,
	val where: Expr<Boolean>? = null,
	val from: FromStatement? = null,
	val returning: Returning? = null,
) : SqlUpdateStatement


/**
 * A builder that can directly build the [UpdateStatement]
 */
interface UpdateBuilderEndStep : SqlUpdateStatement {
	fun build(): UpdateStatement
}


/**
 * A single modification, i.e. a column with its new value
 */
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
