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

/**
 * Builder(-chain) for an [UpdateStatement]
 */
object UpdateBuilder {

	/**
	 * Builder-step with start-options
	 */
	class StartUpdateBuilder {
		/**
		 * Updates rows of the given table
		 */
		fun update(table: Table) = PostTargetBuilder(table)
	}


	/**
	 * Builder-step with options for after the "update"-keyword
	 */
	class PostTargetBuilder(
		private val target: Table
	) {

		/**
		 * specify the modification to apply to each (selected) row
		 */
		fun set(e: List<UpdateElement<*>>) = PostSetBuilder(target, e)


		/**
		 * specify the modification to apply to each (selected) row
		 */
		fun set(vararg e: UpdateElement<*>) = set(e.toList())

	}


	/**
	 * Builder-step with options for after the "set"-statement
	 */
	class PostSetBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>
	) : UpdateBuilderEndStep {

		/**
		 * Build the [UpdateStatement] with the current data
		 */
		override fun build() = UpdateStatement(
			target = target,
			set = set
		)


		/**
		 * Only update the rows that match the given expression
		 */
		fun where(condition: Expr<Boolean>) = PostWhereBuilder(target, set, condition)


		/**
		 * Specify other tables to use data from them in the "set"-clause
		 */
		fun from(e: List<FromElement>) = PostFromBuilder(target, set, null, FromStatement(e))


		/**
		 * Specify other tables to use data from them in the "set"-clause
		 */
		fun from(vararg e: FromElement) = from(e.toList())


		/**
		 * Specify the columns of the updated rows to return
		 */
		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, set, null, null, ReturnColumns(columns))


		/**
		 * Specify the columns of the updated rows to return
		 */
		fun returning(vararg columns: Column<*>) = returning(columns.toList())


		/**
		 * Return all columns of the updated rows
		 */
		fun returningAll() = PostReturningBuilder(target, set, null, null, ReturnAllColumns())

	}


	/**
	 * Builder-step with options for after the "where"-statement
	 */
	class PostWhereBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>
	) : UpdateBuilderEndStep {

		/**
		 * Build the [UpdateStatement] with the current data
		 */
		override fun build() = UpdateStatement(
			target = target,
			set = set,
			where = where
		)


		/**
		 * Specify other tables to use data from them in the "set"-clause
		 */
		fun from(e: List<FromElement>) = PostFromBuilder(target, set, where, FromStatement(e))


		/**
		 * Specify other tables to use data from them in the "set"-clause
		 */
		fun from(vararg e: FromElement) = from(e.toList())


		/**
		 * Specify the columns of the updated rows to return
		 */
		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, set, where, null, ReturnColumns(columns))


		/**
		 * Specify the columns of the updated rows to return
		 */
		fun returning(vararg columns: Column<*>) = returning(columns.toList())


		/**
		 * Return all columns of the updated rows
		 */
		fun returningAll() = PostReturningBuilder(target, set, where, null, ReturnAllColumns())

	}


	/**
	 * Builder-step with options for after the "from"-statement
	 */
	class PostFromBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>?,
		private val from: FromStatement?
	) : UpdateBuilderEndStep {

		/**
		 * Build the [UpdateStatement] with the current data
		 */
		override fun build() = UpdateStatement(
			target = target,
			set = set,
			where = where,
			from = from
		)


		/**
		 * Specify the columns of the updated rows to return
		 */
		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, set, where, from, ReturnColumns(columns))


		/**
		 * Specify the columns of the updated rows to return
		 */
		fun returning(vararg columns: Column<*>) = returning(columns.toList())


		/**
		 * Return all columns of the updated rows
		 */
		fun returningAll() = PostReturningBuilder(target, set, where, from, ReturnAllColumns())

	}


	/**
	 * Builder-step with options for after the "returning"-statement
	 */
	class PostReturningBuilder(
		private val target: Table,
		private val set: List<UpdateElement<*>>,
		private val where: Expr<Boolean>?,
		private val from: FromStatement?,
		private val returning: Returning
	) : UpdateBuilderEndStep {

		/**
		 * Build the [UpdateStatement] with the current data
		 */
		override fun build() = UpdateStatement(
			target = target,
			set = set,
			where = where,
			from = from,
			returning = returning
		)

	}

}

