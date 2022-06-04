package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.ColumnExprGenerator
import de.ruegnerlukas.sqldsl2.grammar.Table
import de.ruegnerlukas.sqldsl2.grammar.TableAlias
import de.ruegnerlukas.sqldsl2.grammar.expr.Column
import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericColumnExprGenerator : ColumnExprGenerator, GenericGeneratorBase<ColumnExpr>() {

	override fun buildToken(e: ColumnExpr): Token {
		return when (e) {
			is Column -> column(e)
			is QualifiedColumn -> qualifiedColumn(e)
			else -> throwUnknownType(e)
		}
	}

	private fun column(e: Column): Token {
		return StringToken(e.columnName)
	}

	private fun qualifiedColumn(e: QualifiedColumn): Token {
		return when (val qualifier = e.qualifier) {
			is Table<*> -> StringToken("${qualifier.tableName}.${e.column.columnName}")
			is TableAlias -> StringToken("${qualifier.alias}.${e.column.columnName}")
			else -> throwUnknownType(qualifier)
		}
	}

}