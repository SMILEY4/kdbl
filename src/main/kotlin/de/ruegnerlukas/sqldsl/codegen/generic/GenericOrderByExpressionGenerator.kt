package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.OrderByExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericOrderByExpressionGenerator(private val genCtx: GeneratorContext) : OrderByExpressionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): Token {
		return ListToken()
			.add(GroupToken(genCtx.select().buildToken(e.column)))
			.add(mapDir(e.dir))
	}

	protected fun mapDir(dir: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir): String {
		return when (dir) {
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir.ASC -> "ASC"
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir.DESC -> "DESC"
		}
	}

}