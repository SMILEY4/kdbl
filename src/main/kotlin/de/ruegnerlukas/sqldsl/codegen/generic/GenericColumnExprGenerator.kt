package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.ColumnExprGenerator
import de.ruegnerlukas.sqldsl.dsl.grammar.column.Column
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericColumnExprGenerator : ColumnExprGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.QualifiedColumn -> qualifiedColumn(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn -> derivedColumn(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn -> aliasColumn(e)
			else -> throwUnknownType(e)
		}
	}

	open fun derivedColumn(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn<*>): Token {
		return StringToken("${e.getParentTable().tableName}.${e.getColumnName()}")
	}

	open fun qualifiedColumn(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.QualifiedColumn<*>): Token {
		return when (e) {
			is Column -> {
				when (val qualifier = e.getParentTable()) {
					is de.ruegnerlukas.sqldsl.dsl.grammar.table.AliasTable -> StringToken("${qualifier.aliasName}.${e.getColumnName()}")
					is de.ruegnerlukas.sqldsl.dsl.grammar.table.StandardTable -> StringToken("${qualifier.tableName}.${e.getColumnName()}")
					else -> throwUnknownType(qualifier)
				}
			}
			else -> throwUnknownType(e)
		}
	}

	open fun aliasColumn(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn<*>): Token {
		return StringToken(e.getColumnName())
	}

}