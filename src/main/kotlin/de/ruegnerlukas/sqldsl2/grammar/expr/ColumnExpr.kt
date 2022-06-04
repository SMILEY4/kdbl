package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.schema.Column

interface ColumnExpr : Expr {
	fun alias(alias: String): AliasSelectExpression {
		return AliasSelectExpression(this, alias)
	}
}

class UnqualifiedColumn(val columnName: String) : ColumnExpr

interface QualifiedColumn : ColumnExpr

fun Column.anyTable(): UnqualifiedColumn {
	return UnqualifiedColumn(this.columnName)
}
