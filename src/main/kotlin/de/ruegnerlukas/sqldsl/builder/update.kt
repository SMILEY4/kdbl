package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expression.Column
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expression.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expression.Returning
import de.ruegnerlukas.sqldsl.dsl.expression.Table
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.FromStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateElement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement

object UpdateBuilder {

	class StartUpdateBuilder {

		fun update(table: Table) = PostTargetBuilder(table)

	}


	class PostTargetBuilder(
		private val target: Table
	) {

		fun set(e: List<UpdateElement<*>>) = PostSetBuilder(target, e)

		fun set(vararg e: UpdateElement<*>) = set(e.toList())

	}


	class PostSetBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			target = target,
			set = set
		)

		fun where(condition: Expr<Boolean>) = PostWhereBuilder(target, set, condition)

		fun from(e: List<FromElement>) = PostFromBuilder(target, set, null, FromStatement(e))

		fun from(vararg e: FromElement) = from(e.toList())

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, set, null, null, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, set, null, null, ReturnAllColumns())

	}


	class PostWhereBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			target = target,
			set = set,
			where = where
		)

		fun from(e: List<FromElement>) = PostFromBuilder(target, set, where, FromStatement(e))

		fun from(vararg e: FromElement) = from(e.toList())

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, set, where, null, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, set, where, null, ReturnAllColumns())

	}


	class PostFromBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>?,
		private val from: FromStatement?
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			target = target,
			set = set,
			where = where,
			from = from
		)

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, set, where, from, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, set, where, from, ReturnAllColumns())

	}


	class PostReturningBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>?,
		private val from: FromStatement?,
		private val returning: Returning
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			target = target,
			set = set,
			where = where,
			from = from,
			returning = returning
		)

	}

}

