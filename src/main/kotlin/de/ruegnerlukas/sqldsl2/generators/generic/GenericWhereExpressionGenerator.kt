package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.WhereExpressionGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl2.grammar.where.WhereExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericWhereExpressionGenerator(private val genCtx: GeneratorContext) : WhereExpressionGenerator, GenericGeneratorBase<WhereExpression>() {

	override fun buildToken(e: WhereExpression): Token {
		return when (e) {
			is ConditionExpr -> genCtx.condition().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}