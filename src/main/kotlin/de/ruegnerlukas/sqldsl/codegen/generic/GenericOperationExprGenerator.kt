package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.OperationExprGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericOperationExprGenerator(private val genCtx: GeneratorContext) : OperationExprGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<*>>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddOperation -> add(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddAllOperation -> addAll(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation -> sub(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.MulOperation -> mul(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation -> div(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConcatOperation -> concat(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr -> condition(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun add(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("+")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun addAll(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AddAllOperation<*>): Token {
		return ListToken().then {
			e.expressions.map { GroupToken(genCtx.expr().buildToken(it)) }.forEachIndexed {index, token ->
				if(index > 0) {
					add("+")
				}
				add(token)
			}
		}
	}

	protected fun sub(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("-")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun mul(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.MulOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("*")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun div(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.DivOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("/")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun concat(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConcatOperation): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("||")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun condition(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr): Token {
		return genCtx.condition().buildToken(e)
	}

}