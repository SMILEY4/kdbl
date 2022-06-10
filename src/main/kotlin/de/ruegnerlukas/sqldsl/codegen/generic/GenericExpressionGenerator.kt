package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.ExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericExpressionGenerator(private val genCtx: GeneratorContext) : ExpressionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*>>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue -> literalValue(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr -> operation(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction -> aggregateFunction(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr -> column(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun literalValue(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>): Token {
		return genCtx.literal().buildToken(e)
	}

	protected fun operation(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<*>): Token {
		return genCtx.operation().buildToken(e)
	}

	protected fun aggregateFunction(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<*>): Token {
		return genCtx.aggFunc().buildToken(e)
	}

	protected fun column(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>): Token {
		return genCtx.columnExpr().buildToken(e)
	}

}