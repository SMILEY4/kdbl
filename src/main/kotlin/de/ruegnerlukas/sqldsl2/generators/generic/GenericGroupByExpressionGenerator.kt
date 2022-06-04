package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.GroupByExpressionGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.Expr
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericGroupByExpressionGenerator(private val genCtx: GeneratorContext) : GroupByExpressionGenerator, GenericGeneratorBase<GroupByExpression>() {

	override fun buildToken(e: GroupByExpression): Token {
		return when (e) {
			is Expr -> genCtx.expr().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}