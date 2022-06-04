package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.OrderByExpressionGenerator
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.tokens.GroupToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericOrderByExpressionGenerator(private val genCtx: GeneratorContext) : OrderByExpressionGenerator, GenericGeneratorBase<OrderByExpression>() {

	override fun buildToken(e: OrderByExpression): Token {
		return ListToken()
			.add(GroupToken(genCtx.select().buildToken(e.column)))
			.add(mapDir(e.dir))
	}

	protected fun mapDir(dir: Dir): String {
		return when (dir) {
			Dir.ASC -> "ASC"
			Dir.DESC -> "DESC"
		}
	}

}