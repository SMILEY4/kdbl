package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.BooleanLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Column
import de.ruegnerlukas.kdbl.dsl.expression.DateLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.NullLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.ReturnAllColumns
import de.ruegnerlukas.kdbl.dsl.expression.ReturnColumns
import de.ruegnerlukas.kdbl.dsl.expression.Returning
import de.ruegnerlukas.kdbl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.StringLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.expression.TimeLiteralExpr
import de.ruegnerlukas.kdbl.dsl.statements.FromElement
import de.ruegnerlukas.kdbl.dsl.statements.FromStatement
import de.ruegnerlukas.kdbl.dsl.statements.UpdateBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.UpdateElement
import de.ruegnerlukas.kdbl.dsl.statements.UpdateStatement
import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime


class ModificationsMap() {

	private val modifications = mutableListOf<UpdateElement<*>>()

	operator fun <T> set(key: Column<T>, value: T?) {
		@Suppress("UNCHECKED_CAST")
		val e = UpdateElement(
			key,
			value?.let {
				when (value) {
					is Boolean -> BooleanLiteralExpr(value)
					is Short -> ShortLiteralExpr(value)
					is Int -> IntLiteralExpr(value)
					is Long -> LongLiteralExpr(value)
					is Float -> FloatLiteralExpr(value)
					is Double -> DoubleLiteralExpr(value)
					is String -> StringLiteralExpr(value)
					is SqlDate -> DateLiteralExpr(value)
					is SqlTime -> TimeLiteralExpr(value)
					else -> throw Exception("Type '$value' not supported")
				} as Expr<T>
			} ?: NullLiteralExpr()
		)
		modifications.add(e)
	}

	internal fun getModification() = modifications.toList()

}


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
		fun set(block: (map: ModificationsMap) -> Unit) = PostSetBuilder(target, ModificationsMap().apply(block).getModification())


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

