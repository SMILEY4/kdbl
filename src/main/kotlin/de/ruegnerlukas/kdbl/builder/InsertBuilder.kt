package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Column
import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.InsertContent
import de.ruegnerlukas.kdbl.dsl.statements.InsertItem
import de.ruegnerlukas.kdbl.dsl.statements.InsertStatement
import de.ruegnerlukas.kdbl.dsl.statements.ItemsInsertContent
import de.ruegnerlukas.kdbl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.QueryStatement
import de.ruegnerlukas.kdbl.dsl.statements.ReturnAllColumns
import de.ruegnerlukas.kdbl.dsl.statements.ReturnColumns
import de.ruegnerlukas.kdbl.dsl.statements.Returning

/**
 * Builder(-chain) for an [InsertStatement]
 */
object InsertBuilder {

	/**
	 * Builder for a single item to insert
	 */
	class InsertItemBuilder {
		/**
		 * start building a single item to insert
		 */
		fun item() = InsertItem()
	}


	/**
	 * Builder-step with start-options
	 */
	class StartInsertBuilder {
		/**
		 * insert items into a table
		 */
		fun insert() = PostInsertBuilder(false)


		/**
		 * insert items into a table. Replace/update if item already exists
		 */
		fun insertOrUpdate() = PostInsertBuilder(true)

	}


	/**
	 * Builder-step with options for after the "insert"-keyword
	 */
	class PostInsertBuilder(
		private val updateExisting: Boolean
	) {
		/**
		 * Insert the items into the given table
		 */
		fun into(table: Table) = PostIntoBuilder(updateExisting, table)
	}


	/**
	 * Builder-step with options for after the "into"-statement
	 */
	class PostIntoBuilder(
		private val updateExisting: Boolean,
		private val target: Table
	) {

		/**
		 * Insert values into the given columns
		 */
		fun columns(columns: List<Column<*>>) = PostFieldsBuilder(updateExisting, target, columns)


		/**
		 * Insert values into the given columns
		 */
		fun columns(vararg columns: Column<*>) = PostFieldsBuilder(updateExisting, target, columns.toList())


		/**
		 * Insert values into all columns. Only use when inserting via a sub-query
		 */
		fun allColumns() = PostFieldsBuilder(updateExisting, target, listOf())

	}


	/**
	 * Builder-step with options for after specifying the columns
	 */
	class PostFieldsBuilder(
		private val updateExisting: Boolean,
		private val target: Table,
		private val fields: List<Column<*>>
	) {

		/**
		 * Insert the result of the given sub-query
		 */
		fun query(query: QueryStatement<*>) = PostContentBuilder(updateExisting, target, fields, query)


		/**
		 * Insert the result of the given sub-query
		 */
		fun query(query: QueryBuilderEndStep<*>) = query(query.build<Any>())


		/**
		 * Insert the given items
		 */
		fun items(items: List<InsertItem>) = PostContentBuilder(updateExisting, target, fields, ItemsInsertContent(items))


		/**
		 * Insert the given items
		 */
		fun items(vararg items: InsertItem) = items(items.toList())

	}


	/**
	 * Builder-step with options for after the "values"-statement
	 */
	class PostContentBuilder(
		private val updateExisting: Boolean,
		private val target: Table,
		private val fields: List<Column<*>>,
		private val content: InsertContent
	) : InsertBuilderEndStep {

		/**
		 * Build the [InsertStatement] with the current data
		 */
		override fun build() = InsertStatement(
			updateExisting = updateExisting,
			target = target,
			fields = fields,
			content = content,
			returning = null
		)


		/**
		 * Specify the columns of the inserted rows to return
		 */
		fun returning(columns: List<Column<*>>) = PostReturningBuilder(updateExisting, target, fields, content, ReturnColumns(columns))


		/**
		 * Specify the columns of the inserted rows to return
		 */
		fun returning(vararg columns: Column<*>) = returning(columns.toList())


		/**
		 * Return all columns of the inserted rows
		 */
		fun returningAll() = PostReturningBuilder(updateExisting, target, fields, content, ReturnAllColumns())

	}


	/**
	 * Builder-step with options for after the "returning"-statement
	 */
	class PostReturningBuilder(
		private val updateExisting: Boolean,
		private val target: Table,
		private val fields: List<Column<*>>,
		private val content: InsertContent,
		private val returning: Returning
	) : InsertBuilderEndStep {

		/**
		 * Build the [InsertStatement] with the current data
		 */
		override fun build() = InsertStatement(
			updateExisting = updateExisting,
			target = target,
			fields = fields,
			content = content,
			returning = returning
		)

	}

}