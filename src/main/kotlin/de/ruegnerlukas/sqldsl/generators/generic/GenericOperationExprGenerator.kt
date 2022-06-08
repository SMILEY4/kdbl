package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.OperationExprGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.AddAllOperation
import de.ruegnerlukas.sqldsl.grammar.expr.AddOperation
import de.ruegnerlukas.sqldsl.grammar.expr.ConcatOperation
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.expr.DivOperation
import de.ruegnerlukas.sqldsl.grammar.expr.MulOperation
import de.ruegnerlukas.sqldsl.grammar.expr.OperationExpr
import de.ruegnerlukas.sqldsl.grammar.expr.SubOperation
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericOperationExprGenerator(private val genCtx: GeneratorContext) : OperationExprGenerator, GenericGeneratorBase<OperationExpr<*>>() {

	override fun buildToken(e: OperationExpr<*>): Token {
		return when (e) {
			is AddOperation -> add(e)
			is AddAllOperation -> addAll(e)
			is SubOperation -> sub(e)
			is MulOperation -> mul(e)
			is DivOperation -> div(e)
			is ConcatOperation -> concat(e)
			is ConditionExpr -> condition(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun add(e: AddOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("+")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun addAll(e: AddAllOperation<*>): Token {
		return ListToken().then {
			e.expressions.map { GroupToken(genCtx.expr().buildToken(it)) }.forEachIndexed {index, token ->
				if(index > 0) {
					add("+")
				}
				add(token)
			}
		}
	}

	protected fun sub(e: SubOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("-")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun mul(e: MulOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("*")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun div(e: DivOperation<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("/")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun concat(e: ConcatOperation): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("||")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun condition(e: ConditionExpr): Token {
		return genCtx.condition().buildToken(e)
	}

}