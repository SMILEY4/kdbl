package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturningColumn
import de.ruegnerlukas.sqldsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement

interface DeleteBuilderEndStep {
	fun build(): DeleteStatement
}

class DeleteBuilder {

	fun deleteFrom(target: CommonTarget) = PostDeleteTargetBuilder(target)

}


class PostDeleteTargetBuilder(private val target: CommonTarget) : DeleteBuilderEndStep {

	fun where(condition: ConditionExpr) = PostDeleteWhereBuilder(target, WhereStatement(condition))

	override fun build(): DeleteStatement {
		return DeleteStatement(
			target = target,
			where = null,
			returning = null
		)
	}

}

class PostDeleteWhereBuilder(private val target: CommonTarget, private val where: WhereStatement) : DeleteBuilderEndStep {

	fun returningAll() = PostDeleteReturningBuilder(target, where, ReturnAllColumnsStatement())

	fun returning(columns: List<ReturningColumn>) = PostDeleteReturningBuilder(target, where, ReturnColumnsStatement(columns))

	fun returning(vararg columns: ReturningColumn) = PostDeleteReturningBuilder(target, where, ReturnColumnsStatement(columns.toList()))

	override fun build(): DeleteStatement {
		return DeleteStatement(
			target = target,
			where = where,
			returning = null
		)
	}

}


class PostDeleteReturningBuilder(private val target: CommonTarget, private val where: WhereStatement, val returning: ReturningStatement) :
	DeleteBuilderEndStep {

	override fun build(): DeleteStatement {
		return DeleteStatement(
			target = target,
			where = where,
			returning = returning
		)
	}

}