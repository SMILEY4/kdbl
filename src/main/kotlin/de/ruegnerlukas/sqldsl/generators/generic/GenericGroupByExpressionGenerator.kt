package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.GroupByExpressionGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericGroupByExpressionGenerator(private val genCtx: GeneratorContext) : GroupByExpressionGenerator, GenericGeneratorBase<GroupByExpression>() {

	override fun buildToken(e: GroupByExpression): Token {
		return when (e) {
			is Expr<*> -> genCtx.expr().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}