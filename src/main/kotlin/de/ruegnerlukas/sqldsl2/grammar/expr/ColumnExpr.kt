package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.table.TableBase

interface ColumnExpr : Expr {
	fun getColumnName(): String
	fun getParentTable(): TableBase
	fun alias(alias: String): AliasSelectExpression {
		return AliasSelectExpression(this, alias)
	}
}

interface QualifiedColumn : ColumnExpr


class DerivedColumn(private val parentTable: DerivedTable, private val columnName: String) : ColumnExpr {
	override fun getColumnName() = columnName
	override fun getParentTable() = parentTable
}