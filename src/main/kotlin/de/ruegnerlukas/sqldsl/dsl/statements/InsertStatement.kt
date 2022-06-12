package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Table

class InsertStatement(
	val onConflict: OnConflict,
	val target: Table,
	val fields: List<Column<*>>,
	val content: InsertContent,
	val returning: Returning?
)

interface InsertContent

class ItemsInsertContent(val items: List<InsertItem>) : InsertContent

class InsertItem {

	private val values: MutableMap<Column<*>, LiteralExpr<*>> = mutableMapOf()

	fun set(column: Column<Int>, value: Int): InsertItem {
		values[column] = IntLiteralExpr(value)
		return this
	}

	fun set(column: Column<Float>, value: Float): InsertItem {
		values[column] = FloatLiteralExpr(value)
		return this
	}

	fun set(column: Column<String>, value: String): InsertItem {
		values[column] = StringLiteralExpr(value)
		return this
	}

	fun set(column: Column<Boolean>, value: Boolean): InsertItem {
		values[column] = BooleanLiteralExpr(value)
		return this
	}

	fun getValue(column: Column<*>) = values[column]

}