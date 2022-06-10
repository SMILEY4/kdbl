package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.WhereExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericWhereExpressionGenerator(private val genCtx: GeneratorContext) : WhereExpressionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereExpression>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr -> genCtx.condition().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}