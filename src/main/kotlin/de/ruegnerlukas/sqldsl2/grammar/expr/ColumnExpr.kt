package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.insert.InsertColumnExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningColumn
import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.table.TableBase
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateColumn
import de.ruegnerlukas.sqldsl2.schema.AnyValueType

interface ColumnExpr<T: AnyValueType> : Expr<T> {
	fun getColumnName(): String
	fun getParentTable(): TableBase
}

interface QualifiedColumn<T: AnyValueType> : ColumnExpr<T>, InsertColumnExpression, UpdateColumn, ReturningColumn {
	fun alias(alias: String): AliasSelectExpression<T> {
		return AliasColumn<T>(alias).assign(this)
	}
}


class DerivedColumn<T: AnyValueType>(private val parentTable: DerivedTable, private val columnName: String) : ColumnExpr<T> {
	override fun getColumnName() = columnName
	override fun getParentTable() = parentTable
}


class AliasColumn<T: AnyValueType>(private val alias: String) : ColumnExpr<T>, AliasSelectExpression<T>, ReturningColumn {

	constructor(expr: SelectExpression<T>, alias: String) : this(alias) {
		assign(expr)
	}

	private var content: SelectExpression<T>? = null

	fun assign(expr: SelectExpression<T>): AliasColumn<T> {
		content = expr
		return this
	}

	fun getContent(): SelectExpression<T> {
		return this.content ?: throw IllegalStateException("No content assigned to alias-column '$alias'")
	}

	override fun getColumnName(): String {
		return alias
	}

	override fun getParentTable(): TableBase {
		throw UnsupportedOperationException("Alias column does not have a parent table")
	}

}