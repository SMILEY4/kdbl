package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.HavingExpressionGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl2.grammar.having.HavingExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericHavingExpressionGenerator(private val genCtx: GeneratorContext) : HavingExpressionGenerator, GenericGeneratorBase<HavingExpression>() {

	override fun buildToken(e: HavingExpression): Token {
		return when (e) {
			is ConditionExpr -> genCtx.condition().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}