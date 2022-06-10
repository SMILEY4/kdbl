package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.HavingExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericHavingExpressionGenerator(private val genCtx: GeneratorContext) : HavingExpressionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingExpression>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr -> genCtx.condition().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}