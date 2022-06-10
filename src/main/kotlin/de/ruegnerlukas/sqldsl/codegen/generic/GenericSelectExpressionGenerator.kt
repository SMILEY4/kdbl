package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.SelectExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericSelectExpressionGenerator(val genCtx: GeneratorContext) : SelectExpressionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.select.AliasSelectExpression -> alias(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.select.ExprSelectExpression -> expression(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.select.AllSelectExpression -> all(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.select.QualifiedAllSelectExpression -> qualifiedAll(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun expression(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.ExprSelectExpression<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*> -> genCtx.columnExpr().buildToken(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*> -> genCtx.expr().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun all(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.AllSelectExpression): Token {
		return StringToken("*")
	}

	protected fun qualifiedAll(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.QualifiedAllSelectExpression): Token {
		return when (val qualifier = e.qualifier) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.table.StandardTable -> StringToken("${qualifier.tableName}.*")
			is de.ruegnerlukas.sqldsl.dsl.grammar.table.AliasTable -> StringToken("${qualifier.aliasName}.*")
			is de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable -> StringToken("${qualifier.tableName}.*")
			else -> throwUnknownType(qualifier)
		}
	}

	protected fun alias(e: de.ruegnerlukas.sqldsl.dsl.grammar.select.AliasSelectExpression<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn -> ListToken()
				.add(GroupToken(buildToken(e.getContent())))
				.add("AS")
				.add(e.getColumnName())
			else -> throwUnknownType(e)
		}
	}

}