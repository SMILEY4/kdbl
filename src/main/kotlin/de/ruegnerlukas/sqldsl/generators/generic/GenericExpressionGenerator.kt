package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.ExpressionGenerator
import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.grammar.expr.AggregateFunction
import de.ruegnerlukas.sqldsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.grammar.expr.LiteralValue
import de.ruegnerlukas.sqldsl.grammar.expr.OperationExpr
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericExpressionGenerator(private val genCtx: GeneratorContext) : ExpressionGenerator, GenericGeneratorBase<Expr<*>>() {

	override fun buildToken(e: Expr<*>): Token {
		return when (e) {
			is LiteralValue -> literalValue(e)
			is OperationExpr -> operation(e)
			is AggregateFunction -> aggregateFunction(e)
			is ColumnExpr -> column(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun literalValue(e: LiteralValue<*>): Token {
		return genCtx.literal().buildToken(e)
	}

	protected fun operation(e: OperationExpr<*>): Token {
		return genCtx.operation().buildToken(e)
	}

	protected fun aggregateFunction(e: AggregateFunction<*>): Token {
		return genCtx.aggFunc().buildToken(e)
	}

	protected fun column(e: ColumnExpr<*>): Token {
		return genCtx.columnExpr().buildToken(e)
	}

}