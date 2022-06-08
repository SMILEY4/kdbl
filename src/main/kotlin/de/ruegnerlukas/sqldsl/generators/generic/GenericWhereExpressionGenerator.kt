package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.WhereExpressionGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.where.WhereExpression
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericWhereExpressionGenerator(private val genCtx: GeneratorContext) : WhereExpressionGenerator, GenericGeneratorBase<WhereExpression>() {

	override fun buildToken(e: WhereExpression): Token {
		return when (e) {
			is ConditionExpr -> genCtx.condition().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}