package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.ConditionGenerator
import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericConditionGenerator(private val genCtx: GeneratorContext) : ConditionGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndCondition -> and(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndChainCondition -> andChain(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrCondition -> or(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrChainCondition -> orChain(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition<*> -> equal(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotEqualCondition<*> -> notEqual(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterThanCondition<*> -> greater(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterOrEqualThanCondition<*> -> greaterOrEqual(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessThanCondition<*> -> less(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessOrEqualThanCondition<*> -> lessOrEqual(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotCondition -> not(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.IsNotNullCondition<*> -> isNotNull(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.IsNullCondition<*> -> isNull(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.InListCondition<*> -> inList(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInListCondition<*> -> notInList(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.InSubQueryCondition<*> -> inSubQuery(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInSubQueryCondition<*> -> notInSubQuery(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.BetweenCondition<*> -> between(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.LikeCondition -> like(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun and(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndCondition): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.left)))
			.add("AND")
			.add(GroupToken(buildToken(e.right)))
	}

	protected fun andChain(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndChainCondition): Token {
		if (e.expressions.isEmpty()) {
			throw IllegalStateException("And-Chain must have at least one element")
		} else {
			return ListToken().then {
				e.expressions.forEachIndexed { index, cond ->
					if (index > 0) {
						add("AND")
					}
					add(GroupToken(buildToken(cond)))
				}
			}
		}
	}

	protected fun or(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrCondition): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.left)))
			.add("OR")
			.add(GroupToken(buildToken(e.right)))
	}

	protected fun orChain(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrChainCondition): Token {
		if (e.expressions.isEmpty()) {
			throw IllegalStateException("Or-Chain must have at least one element")
		} else {
			return ListToken().then {
				e.expressions.forEachIndexed { index, cond ->
					if (index > 0) {
						add("OR")
					}
					add(GroupToken(buildToken(cond)))
				}
			}
		}
	}

	protected fun equal(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun notEqual(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotEqualCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("!=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun greater(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add(">")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun greaterOrEqual(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterOrEqualThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add(">=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun less(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("<")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun lessOrEqual(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessOrEqualThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("<=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun not(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotCondition): Token {
		return ListToken()
			.add("NOT")
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
	}

	protected fun isNotNull(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.IsNotNullCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IS NOT NULL")
	}

	protected fun isNull(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.IsNullCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IS NULL")
	}

	protected fun inList(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.InListCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IN")
			.add(genCtx.literal().buildToken(e.list))
	}

	protected fun notInList(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInListCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("NOT IN")
			.add(genCtx.literal().buildToken(e.list))
	}

	protected fun inSubQuery(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.InSubQueryCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IN")
			.add(GroupToken(genCtx.query().buildToken(e.query)))
	}

	protected fun notInSubQuery(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInSubQueryCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("NOT IN")
			.add(GroupToken(genCtx.query().buildToken(e.query)))
	}

	protected fun between(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.BetweenCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("BETWEEN")
			.add(GroupToken(genCtx.expr().buildToken(e.lower)))
			.add("AND")
			.add(GroupToken(genCtx.expr().buildToken(e.upper)))
	}

	protected fun like(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LikeCondition): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("LIKE")
			.add("'${e.pattern}'")
	}

}