package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.SelectExpressionGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.ExprSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.QualifiedAllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.grammar.table.AliasTable
import de.ruegnerlukas.sqldsl.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl.grammar.table.StandardTable
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.StringToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericSelectExpressionGenerator(val genCtx: GeneratorContext) : SelectExpressionGenerator, GenericGeneratorBase<SelectExpression<*>>() {

	override fun buildToken(e: SelectExpression<*>): Token {
		return when (e) {
			is AliasSelectExpression -> alias(e)
			is ExprSelectExpression -> expression(e)
			is AllSelectExpression -> all(e)
			is QualifiedAllSelectExpression -> qualifiedAll(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun expression(e: ExprSelectExpression<*>): Token {
		return when (e) {
			is ColumnExpr<*> -> genCtx.columnExpr().buildToken(e)
			is Expr<*> -> genCtx.expr().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun all(e: AllSelectExpression): Token {
		return StringToken("*")
	}

	protected fun qualifiedAll(e: QualifiedAllSelectExpression): Token {
		return when (val qualifier = e.qualifier) {
			is StandardTable -> StringToken("${qualifier.tableName}.*")
			is AliasTable -> StringToken("${qualifier.aliasName}.*")
			is DerivedTable -> StringToken("${qualifier.tableName}.*")
			else -> throwUnknownType(qualifier)
		}
	}

	protected fun alias(e: AliasSelectExpression<*>): Token {
		return when (e) {
			is AliasColumn -> ListToken()
				.add(GroupToken(buildToken(e.getContent())))
				.add("AS")
				.add(e.getColumnName())
			else -> throwUnknownType(e)
		}
	}

}