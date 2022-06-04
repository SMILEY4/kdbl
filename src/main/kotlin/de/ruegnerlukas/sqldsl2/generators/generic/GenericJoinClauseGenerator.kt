package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.JoinClauseGenerator
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl2.tokens.CsvListToken
import de.ruegnerlukas.sqldsl2.tokens.GroupToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericJoinClauseGenerator(private val genCtx: GeneratorContext) : JoinClauseGenerator, GenericGeneratorBase<JoinClause>() {

	override fun buildToken(e: JoinClause): Token {
		return ListToken()
			.add(GroupToken(genCtx.from().buildToken(e.left)))
			.add(mapOp(e.op))
			.add(GroupToken(genCtx.from().buildToken(e.right)))
			.add(GroupToken(constraint(e.constraint)))
	}

	protected fun mapOp(op: JoinOp): String {
		return when (op) {
			JoinOp.LEFT -> "JOIN"
			JoinOp.INNER -> "INNER JOIN"
			JoinOp.CROSS -> "CROSS JOIN"
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