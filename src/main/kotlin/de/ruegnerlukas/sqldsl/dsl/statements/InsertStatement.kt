package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.BlobLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.DateLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.DoubleLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LongLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.ShortLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.expr.TimeLiteralExpr
import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

class InsertStatement(
	val onConflict: OnConflict,
	val target: Table,
	val fields: List<Column<*>>,
	val content: InsertContent,
	val returning: Returning?
)


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
	fun set(column: Column<ByteArray>, value: ByteArray) = this.apply { values[column] = BlobLiteralExpr(value) }

	override fun getValue(column: Column<*>) = values[column]

}


interface InsertBuilderEndStep {
	fun build(): InsertStatement
}