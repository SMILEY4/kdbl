package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Column
import de.ruegnerlukas.sqldsl.dsl.expression.DateLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Returning
import de.ruegnerlukas.sqldsl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Table
import de.ruegnerlukas.sqldsl.dsl.expression.TimeLiteralExpr
import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

interface SqlInsertStatement

class InsertStatement(
	val target: Table,
	val fields: List<Column<*>>,
	val content: InsertContent,
	val returning: Returning?
) : SqlInsertStatement

interface InsertBuilderEndStep : SqlInsertStatement {
	fun build(): InsertStatement
}


interface InsertContent


class ItemsInsertContent(val items: List<InsertItem>) : InsertContent


interface InsertItem {
	fun getValue(column: Column<*>): LiteralExpr<*>?
}


class InsertItemImpl : InsertItem {

	private val values: MutableMap<Column<*>, LiteralExpr<*>> = mutableMapOf()

	fun set(column: Column<Boolean>, value: Boolean) = this.apply { values[column] = BooleanLiteralExpr(value) }
	fun set(column: Column<Short>, value: Short) = this.apply { values[column] = ShortLiteralExpr(value) }
	fun set(column: Column<Int>, value: Int) = this.apply { values[column] = IntLiteralExpr(value) }
	fun set(column: Column<Long>, value: Long) = this.apply { values[column] = LongLiteralExpr(value) }
	fun set(column: Column<Float>, value: Float) = this.apply { values[column] = FloatLiteralExpr(value) }
	fun set(column: Column<Double>, value: Double) = this.apply { values[column] = DoubleLiteralExpr(value) }
	fun set(column: Column<String>, value: String) = this.apply { values[column] = StringLiteralExpr(value) }
	fun set(column: Column<SqlDate>, value: SqlDate) = this.apply { values[column] = DateLiteralExpr(value) }
	fun set(column: Column<SqlTime>, value: SqlTime) = this.apply { values[column] = TimeLiteralExpr(value) }

	@JvmName("set_boolean") fun set(column: Column<Boolean>, value: LiteralExpr<Boolean>) = this.apply { values[column] = value }
	@JvmName("set_short") fun set(column: Column<Short>, value: LiteralExpr<Short>) = this.apply { values[column] = value }
	@JvmName("set_int") fun set(column: Column<Int>, value: LiteralExpr<Int>) = this.apply { values[column] = value }
	@JvmName("set_long") fun set(column: Column<Long>, value: LiteralExpr<Long>) = this.apply { values[column] = value }
	@JvmName("set_float") fun set(column: Column<Float>, value: LiteralExpr<Float>) = this.apply { values[column] = value }
	@JvmName("set_double") fun set(column: Column<Double>, value: LiteralExpr<Double>) = this.apply { values[column] = value }
	@JvmName("set_string") fun set(column: Column<String>, value: LiteralExpr<String>) = this.apply { values[column] = value }
	@JvmName("set_date") fun set(column: Column<SqlDate>, value: LiteralExpr<SqlDate>) = this.apply { values[column] = value }
	@JvmName("set_time") fun set(column: Column<SqlTime>, value: LiteralExpr<SqlTime>) = this.apply { values[column] = value }

	override fun getValue(column: Column<*>) = values[column]

}

