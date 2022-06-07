package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.ColumnExprGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.DerivedColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.table.AliasTable
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.table.StandardTable
import de.ruegnerlukas.sqldsl2.schema.Column
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericColumnExprGenerator : ColumnExprGenerator, GenericGeneratorBase<ColumnExpr>() {

	override fun buildToken(e: ColumnExpr): Token {
		return when (e) {
			is QualifiedColumn -> qualifiedColumn(e)
			is DerivedColumn -> derivedColumn(e)
			is AliasColumn -> aliasColumn(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun derivedColumn(e: DerivedColumn): Token {
		return StringToken("${e.getParentTable().tableName}.${e.getColumnName()}")
	}

	protected fun qualifiedColumn(e: QualifiedColumn): Token {
		return when (e) {
			is Column -> {
				when (val qualifier = e.getParentTable()) {
					is AliasTable -> StringToken("${qualifier.aliasName}.${e.getColumnName()}")
					is StandardTable -> StringToken("${qualifier.tableName}.${e.getColumnName()}")
					else -> throwUnknownType(qualifier)
				}
			}
			else -> throwUnknownType(e)
		}
	}

	protected fun aliasColumn(e: AliasColumn): Token {
		return StringToken(e.getColumnName())
	}

}