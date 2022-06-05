package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.table.TableBase

interface ColumnExpr : Expr {
	fun getColumnName(): String
	fun getParentTable(): TableBase
}

interface QualifiedColumn : ColumnExpr {
	fun alias(alias: String): AliasSelectExpression {
		return AliasColumn(alias).assign(this)
	}
}


class DerivedColumn(private val parentTable: DerivedTable, private val columnName: String) : ColumnExpr {
	override fun getColumnName() = columnName
	override fun getParentTable() = parentTable
}


class AliasColumn(private val alias: String): ColumnExpr, AliasSelectExpression {

	constructor(expr: SelectExpression, alias: String): this(alias) {
		assign(expr)
	}

	private var content: SelectExpression? = null

	fun assign(expr: SelectExpression): AliasColumn {
		content = expr
		return this
	}

	fun getContent(): SelectExpression {
		return this.content ?: throw IllegalStateException("No content assigned to alias-column '$alias'")
	}

	override fun getColumnName(): String {
		return alias
	}

	override fun getParentTable(): TableBase {
		throw UnsupportedOperationException("Alias column does not have a parent table")
	}

}