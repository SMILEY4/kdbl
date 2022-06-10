package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.GroupByExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericGroupByExpressionGenerator(private val genCtx: GeneratorContext) : GroupByExpressionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*> -> genCtx.expr().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}