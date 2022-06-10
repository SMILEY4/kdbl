package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.JoinClauseGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericJoinClauseGenerator(private val genCtx: GeneratorContext) : JoinClauseGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause): Token {
		return ListToken()
			.add(sourceTable(e.left))
			.add(mapOp(e.op))
			.add(sourceTable(e.right))
			.add(constraint(e.constraint))
	}

	protected fun sourceTable(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression): Token {
		return when(e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.from.QueryFromExpression -> GroupToken(genCtx.from().buildToken(e))
			is de.ruegnerlukas.sqldsl.dsl.grammar.from.JoinFromExpression -> GroupToken(genCtx.from().buildToken(e))
			else -> genCtx.from().buildToken(e)
		}
	}

	protected fun mapOp(op: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp): String {
		return when (op) {
			de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER -> "JOIN"
			de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.LEFT -> "LEFT JOIN"
			de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.RIGHT -> "RIGHT JOIN"
			de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.FULL -> "FULL JOIN"
		}
	}

	protected fun constraint(constraint: de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinConstraint): Token {
		return when (constraint) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.join.ConditionJoinConstraint -> conditionConstraint(constraint)
			is de.ruegnerlukas.sqldsl.dsl.grammar.join.UsingJoinConstraint -> usingConstraint(constraint)
			else -> throwUnknownType(constraint)
		}
	}

	protected fun conditionConstraint(constraint: de.ruegnerlukas.sqldsl.dsl.grammar.join.ConditionJoinConstraint): Token {
		return ListToken()
			.add("ON")
			.add(GroupToken(genCtx.condition().buildToken(constraint.condition)))
	}

	protected fun usingConstraint(constraint: de.ruegnerlukas.sqldsl.dsl.grammar.join.UsingJoinConstraint): Token {
		return ListToken()
			.add("USING")
			.add(CsvListToken(constraint.columns.map { genCtx.columnExpr().buildToken(it) }))
	}

}