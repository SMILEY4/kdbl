package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.ConditionGenerator
import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.grammar.expr.AndChainCondition
import de.ruegnerlukas.sqldsl.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl.grammar.expr.BetweenCondition
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.InListCondition
import de.ruegnerlukas.sqldsl.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl.grammar.expr.LessThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.LikeCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotEqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotInListCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotInSubQueryCondition
import de.ruegnerlukas.sqldsl.grammar.expr.OrChainCondition
import de.ruegnerlukas.sqldsl.grammar.expr.OrCondition
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericConditionGenerator(private val genCtx: GeneratorContext) : ConditionGenerator, GenericGeneratorBase<ConditionExpr>() {

	override fun buildToken(e: ConditionExpr): Token {
		return when (e) {
			is AndCondition -> and(e)
			is AndChainCondition -> andChain(e)
			is OrCondition -> or(e)
			is OrChainCondition -> orChain(e)
			is EqualCondition<*> -> equal(e)
			is NotEqualCondition<*> -> notEqual(e)
			is GreaterThanCondition<*> -> greater(e)
			is GreaterOrEqualThanCondition<*> -> greaterOrEqual(e)
			is LessThanCondition<*> -> less(e)
			is NotCondition -> not(e)
			is IsNotNullCondition<*> -> isNotNull(e)
			is IsNullCondition<*> -> isNull(e)
			is InListCondition<*> -> inList(e)
			is NotInListCondition<*> -> notInList(e)
			is InSubQueryCondition<*> -> inSubQuery(e)
			is NotInSubQueryCondition<*> -> notInSubQuery(e)
			is BetweenCondition<*> -> between(e)
			is LikeCondition -> like(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun and(e: AndCondition): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.left)))
			.add("AND")
			.add(GroupToken(buildToken(e.right)))
	}

	protected fun andChain(e: AndChainCondition): Token {
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

	protected fun or(e: OrCondition): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.left)))
			.add("OR")
			.add(GroupToken(buildToken(e.right)))
	}

	protected fun orChain(e: OrChainCondition): Token {
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

	protected fun equal(e: EqualCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun notEqual(e: NotEqualCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("!=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun greater(e: GreaterThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add(">")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun greaterOrEqual(e: GreaterOrEqualThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add(">=")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun less(e: LessThanCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.left)))
			.add("<")
			.add(GroupToken(genCtx.expr().buildToken(e.right)))
	}

	protected fun not(e: NotCondition): Token {
		return ListToken()
			.add("NOT")
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
	}

	protected fun isNotNull(e: IsNotNullCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IS NOT NULL")
	}

	protected fun isNull(e: IsNullCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IS NULL")
	}

	protected fun inList(e: InListCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IN")
			.add(genCtx.literal().buildToken(e.list))
	}

	protected fun notInList(e: NotInListCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("NOT IN")
			.add(genCtx.literal().buildToken(e.list))
	}

	protected fun inSubQuery(e: InSubQueryCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("IN")
			.add(GroupToken(genCtx.query().buildToken(e.query)))
	}

	protected fun notInSubQuery(e: NotInSubQueryCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("NOT IN")
			.add(GroupToken(genCtx.query().buildToken(e.query)))
	}

	protected fun between(e: BetweenCondition<*>): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("BETWEEN")
			.add(GroupToken(genCtx.expr().buildToken(e.lower)))
			.add("AND")
			.add(GroupToken(genCtx.expr().buildToken(e.upper)))
	}

	protected fun like(e: LikeCondition): Token {
		return ListToken()
			.add(GroupToken(genCtx.expr().buildToken(e.expression)))
			.add("LIKE")
			.add("'${e.pattern}'")
	}

}