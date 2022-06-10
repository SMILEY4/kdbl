package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn
import de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement

interface DeleteBuilderEndStep {
	fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement
}

class DeleteBuilder {

	fun deleteFrom(target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget) = PostDeleteTargetBuilder(target)

}


class PostDeleteTargetBuilder(private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget) : DeleteBuilderEndStep {

	fun where(condition: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) = PostDeleteWhereBuilder(target,
		de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement(condition)
	)

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement {
		return de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement(
			target = target,
			where = null,
			returning = null
		)
	}

}

class PostDeleteWhereBuilder(private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget, private val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement) : DeleteBuilderEndStep {

	fun returningAll() = PostDeleteReturningBuilder(target, where, de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnAllColumnsStatement())

	fun returning(columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn>) = PostDeleteReturningBuilder(target, where,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(columns)
	)

	fun returning(vararg columns: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn) = PostDeleteReturningBuilder(target, where,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(columns.toList())
	)

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement {
		return de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement(
			target = target,
			where = where,
			returning = null
		)
	}

}


class PostDeleteReturningBuilder(private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget, private val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement, val returning: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement) :
	DeleteBuilderEndStep {

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement {
		return de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement(
			target = target,
			where = where,
			returning = returning
		)
	}

}