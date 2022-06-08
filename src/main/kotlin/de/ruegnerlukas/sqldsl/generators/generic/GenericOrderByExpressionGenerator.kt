package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.OrderByExpressionGenerator
import de.ruegnerlukas.sqldsl.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.Token

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