package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.ColumnExprGenerator
import de.ruegnerlukas.sqldsl2.grammar.TableAlias
import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.UnqualifiedColumn
import de.ruegnerlukas.sqldsl2.schema.Column
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericColumnExprGenerator : ColumnExprGenerator, GenericGeneratorBase<ColumnExpr>() {

	override fun buildToken(e: ColumnExpr): Token {
		return when (e) {
			is UnqualifiedColumn -> unqualifiedColumn(e)
			is QualifiedColumn -> qualifiedColumn(e)
			else -> throwUnknownType(e)
		}
	}

	private fun unqualifiedColumn(e: UnqualifiedColumn): Token {
		return StringToken(e.columnName)
	}

	private fun qualifiedColumn(e: QualifiedColumn): Token {
		return when (e) {
			is Column -> {
				when (val qualifier = e.parentTable) {
					is TableAlias -> StringToken("${qualifier.alias}.${e.columnName}")
					else -> StringToken("${qualifier.tableName}.${e.columnName}")
				}
			}
			else -> throwUnknownType(e)
		}
	}

}