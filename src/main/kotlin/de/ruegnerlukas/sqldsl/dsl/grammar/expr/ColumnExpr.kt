package de.ruegnerlukas.sqldsl.dsl.grammar.expr

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType

interface ColumnExpr<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T> {
	fun getColumnName(): String
	fun getParentTable(): de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase
}

interface QualifiedColumn<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<T>,
	de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression, de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateColumn,
	de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn {
	fun alias(alias: String): de.ruegnerlukas.sqldsl.dsl.grammar.select.AliasSelectExpression<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn<T>(alias).assign(this)
	}
}


class DerivedColumn<T: AnyValueType>(private val parentTable: de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable, private val columnName: String) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<T> {
	override fun getColumnName() = columnName
	override fun getParentTable() = parentTable
}


class AliasColumn<T: AnyValueType>(private val alias: String) : de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<T>,
	de.ruegnerlukas.sqldsl.dsl.grammar.select.AliasSelectExpression<T>, de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn {

	constructor(expr: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>, alias: String) : this(alias) {
		assign(expr)
	}

	private var content: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>? = null

	fun assign(expr: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>): de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn<T> {
		content = expr
		return this
	}

	fun getContent(): de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T> {
		return this.content ?: throw IllegalStateException("No content assigned to alias-column '$alias'")
	}

	override fun getColumnName(): String {
		return alias
	}

	override fun getParentTable(): de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase {
		throw UnsupportedOperationException("Alias column does not have a parent table")
	}

}