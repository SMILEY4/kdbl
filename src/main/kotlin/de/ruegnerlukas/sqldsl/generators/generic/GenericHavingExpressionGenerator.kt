package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.HavingExpressionGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.having.HavingExpression
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericHavingExpressionGenerator(private val genCtx: GeneratorContext) : HavingExpressionGenerator, GenericGeneratorBase<HavingExpression>() {

	override fun buildToken(e: HavingExpression): Token {
		return when (e) {
			is ConditionExpr -> genCtx.condition().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}