package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.Table
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

		fun or(onConflict: OnConflict) = PostConflictBuilder(target, onConflict)

		fun orRollback() = or(OnConflict.ROLLBACK)
		fun orAbort() = or(OnConflict.ABORT)
		fun orFail() = or(OnConflict.FAIL)
		fun orIgnore() = or(OnConflict.IGNORE)
		fun orReplace() = or(OnConflict.REPLACE)

		fun set(e: List<UpdateElement<*>>) = PostSetBuilder(target, OnConflict.ABORT, e)

		fun set(vararg e: UpdateElement<*>) = set(e.toList())

	}


	class PostConflictBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
	) {

		fun set(e: List<UpdateElement<*>>) = PostSetBuilder(target, onConflict, e)

		fun set(vararg e: UpdateElement<*>) = set(e.toList())

	}


	class PostSetBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val set: List<UpdateElement<*>>
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			onConflict = onConflict,
			target = target,
			set = set
		)

		fun where(condition: Expr<Boolean>) = PostWhereBuilder(target, onConflict, set, condition)

		fun from(e: List<FromElement>) = PostFromBuilder(target, onConflict, set, null, FromStatement(e))

		fun from(vararg e: FromElement) = from(e.toList())

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, onConflict, set, null, null, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, onConflict, set, null, null, ReturnAllColumns())

	}


	class PostWhereBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			onConflict = onConflict,
			target = target,
			set = set,
			where = where
		)

		fun from(e: List<FromElement>) = PostFromBuilder(target, onConflict, set, where, FromStatement(e))

		fun from(vararg e: FromElement) = from(e.toList())

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, onConflict, set, where, null, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, onConflict, set, where, null, ReturnAllColumns())

	}


	class PostFromBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>?,
		private val from: FromStatement?
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			onConflict = onConflict,
			target = target,
			set = set,
			where = where,
			from = from
		)

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, onConflict, set, where, from, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, onConflict, set, where, from, ReturnAllColumns())

	}


	class PostReturningBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>?,
		private val from: FromStatement?,
		private val returning: Returning
	) : UpdateBuilderEndStep {

		override fun build() = UpdateStatement(
			onConflict = onConflict,
			target = target,
			set = set,
			where = where,
			from = from,
			returning = returning
		)

	}

}

