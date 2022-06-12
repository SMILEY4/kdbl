package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement

object DeleteBuilder {

	class StartDeleteBuilder {
		fun delete() = PostDeleteBuilder()

	}

	class PostDeleteBuilder {

		fun from(table: Table) = PostFromBuilder(table)

	}

	class PostFromBuilder(
		private val target: Table
	) : DeleteBuilderEndStep {

		override fun build() = DeleteStatement(
			target = target
		)

		fun where(condition: Expr<Boolean>) = PostWhereBuilder(target, condition)

	}


	class PostWhereBuilder(
		private val target: Table,
		private val where: Expr<Boolean>
	) : DeleteBuilderEndStep {

		override fun build() = DeleteStatement(
			target = target,
			where = where
		)

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, where, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, where, ReturnAllColumns())

	}


	class PostReturningBuilder(
		private val target: Table,
		private val where: Expr<Boolean>?,
		private val returning: Returning
	) : DeleteBuilderEndStep {

		override fun build() = DeleteStatement(
			target = target,
			where = where,
			returning = returning
		)

	}

}