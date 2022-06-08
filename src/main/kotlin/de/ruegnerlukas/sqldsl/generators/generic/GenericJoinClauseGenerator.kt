package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.JoinClauseGenerator
import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.grammar.from.JoinFromExpression
import de.ruegnerlukas.sqldsl.grammar.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.grammar.join.JoinConstraint
import de.ruegnerlukas.sqldsl.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericJoinClauseGenerator(private val genCtx: GeneratorContext) : JoinClauseGenerator, GenericGeneratorBase<JoinClause>() {

	override fun buildToken(e: JoinClause): Token {
		return ListToken()
			.add(sourceTable(e.left))
			.add(mapOp(e.op))
			.add(sourceTable(e.right))
			.add(constraint(e.constraint))
	}

	protected fun sourceTable(e: FromExpression): Token {
		return when(e) {
			is QueryFromExpression -> GroupToken(genCtx.from().buildToken(e))
			is JoinFromExpression-> GroupToken(genCtx.from().buildToken(e))
			else -> genCtx.from().buildToken(e)
		}
	}

	protected fun mapOp(op: JoinOp): String {
		return when (op) {
			JoinOp.INNER -> "JOIN"
			JoinOp.LEFT -> "LEFT JOIN"
			JoinOp.RIGHT -> "RIGHT JOIN"
			JoinOp.FULL -> "FULL JOIN"
		}
	}

	protected fun constraint(constraint: JoinConstraint): Token {
		return when (constraint) {
			is ConditionJoinConstraint -> conditionConstraint(constraint)
			is UsingJoinConstraint -> usingConstraint(constraint)
			else -> throwUnknownType(constraint)
		}
	}

	protected fun conditionConstraint(constraint: ConditionJoinConstraint): Token {
		return ListToken()
			.add("ON")
			.add(GroupToken(genCtx.condition().buildToken(constraint.condition)))
	}

	protected fun usingConstraint(constraint: UsingJoinConstraint): Token {
		return ListToken()
			.add("USING")
			.add(CsvListToken(constraint.columns.map { genCtx.columnExpr().buildToken(it) }))
	}

}