package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Column
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.ReturnAllColumns
import de.ruegnerlukas.kdbl.dsl.expression.ReturnColumns
import de.ruegnerlukas.kdbl.dsl.expression.Returning
import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.statements.DeleteBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.DeleteStatement

/**
 * Builder(-chain) for a [DeleteStatement]
 */
object DeleteBuilder {

	/**
	 * Builder-step with start-options
	 */
	class StartDeleteBuilder {
		/**
		 * Delete rows of a table
		 */
		fun delete() = PostDeleteBuilder()
	}


	/**
	 * Builder-step with options for after the "delete"-keyword
	 */
	class PostDeleteBuilder {

		/**
		 * Delete rows from the given table
		 */
		fun from(table: Table) = PostFromBuilder(table)

	}


	/**
	 * Builder-step with options for after the "from"-statement
	 */
	class PostFromBuilder(
		private val target: Table
	) : DeleteBuilderEndStep {

		/**
		 * Build the [DeleteStatement] with the current data
		 */
		override fun build() = DeleteStatement(
			target = target
		)


		/**
		 * Only delete the rows that match the given expression
		 */
		fun where(condition: Expr<Boolean>) = PostWhereBuilder(target, condition)

	}


	/**
	 * Builder-step with options for after the "where"-statement
	 */
	class PostWhereBuilder(
		private val target: Table,
		private val where: Expr<Boolean>
	) : DeleteBuilderEndStep {

		/**
		 * Build the [DeleteStatement] with the current data
		 */
		override fun build() = DeleteStatement(
			target = target,
			where = where
		)


		/**
		 * Specify the columns of the deleted rows to return
		 */
		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, where, ReturnColumns(columns))


		/**
		 * Specify the columns of the deleted rows to return
		 */
		fun returning(vararg columns: Column<*>) = returning(columns.toList())


		/**
		 * Return all columns of the deleted rows
		 */
		fun returningAll() = PostReturningBuilder(target, where, ReturnAllColumns())

	}


	/**
	 * Builder-step with options for after the "returning"-statement
	 */
	class PostReturningBuilder(
		private val target: Table,
		private val where: Expr<Boolean>?,
		private val returning: Returning
	) : DeleteBuilderEndStep {

		/**
		 * Build the [DeleteStatement] with the current data
		 */
		override fun build() = DeleteStatement(
			target = target,
			where = where,
			returning = returning
		)

	}

}