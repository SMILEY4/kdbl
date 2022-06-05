package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.OperationExprGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AddOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.ConcatOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.DivOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.MulOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.OperationExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.SubOperation
import de.ruegnerlukas.sqldsl2.tokens.GroupToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericOperationExprGenerator(private val genCtx: GeneratorContext) : OperationExprGenerator, GenericGeneratorBase<OperationExpr>() {

	override fun buildToken(e: OperationExpr): Token {
		return when (e) {
			is AddOperation -> add(e)
			is SubOperation -> sub(e)
			is MulOperation -> mul(e)
			is DivOperation -> div(e)
			is ConcatOperation -> concat(e)
			is ConditionExpr -> condition(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun add(e: AddOperation): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("+")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun sub(e: SubOperation): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("-")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun mul(e: MulOperation): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("*")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun div(e: DivOperation): Token {
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