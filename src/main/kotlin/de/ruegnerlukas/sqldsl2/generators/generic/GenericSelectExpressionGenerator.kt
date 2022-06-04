package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.SelectExpressionGenerator
import de.ruegnerlukas.sqldsl2.grammar.TableAlias
import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.Expr
import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.ExprSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.QualifiedAllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.tokens.GroupToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericSelectExpressionGenerator(val genCtx: GeneratorContext) : SelectExpressionGenerator, GenericGeneratorBase<SelectExpression>() {

	override fun buildToken(e: SelectExpression): Token {
		return when (e) {
			is ExprSelectExpression -> expression(e)
			is AllSelectExpression -> all(e)
			is QualifiedAllSelectExpression -> qualifiedAll(e)
			is AliasSelectExpression -> alias(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun expression(e: ExprSelectExpression): Token {
		return when (e) {
			is ColumnExpr -> genCtx.columnExpr().buildToken(e)
			is Expr -> genCtx.expr().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun all(e: AllSelectExpression): Token {
		return StringToken("*")
	}

	protected fun qualifiedAll(e: QualifiedAllSelectExpression): Token {
		return when (val qualifier = e.qualifier) {
			is Table<*> -> StringToken("${qualifier.tableName}.*")
			is TableAlias -> StringToken("${qualifier.alias}.*")
			else -> throwUnknownType(e)
		}
	}

	protected fun alias(e: AliasSelectExpression): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.expr)))
			.add("AS")
			.add(e.alias)
	}

}