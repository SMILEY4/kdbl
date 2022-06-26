package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Column
import de.ruegnerlukas.kdbl.dsl.statements.ReturnAllColumns
import de.ruegnerlukas.kdbl.dsl.statements.ReturnColumns
import de.ruegnerlukas.kdbl.dsl.statements.Returning
import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.InsertContent
import de.ruegnerlukas.kdbl.dsl.statements.InsertItem
import de.ruegnerlukas.kdbl.dsl.statements.InsertStatement
import de.ruegnerlukas.kdbl.dsl.statements.ItemsInsertContent
import de.ruegnerlukas.kdbl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.QueryStatement

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
		fun insert() = PostInsertBuilder()
	}


	/**
	 * Builder-step with options for after the "insert"-keyword
	 */
	class PostInsertBuilder {
		/**
		 * Insert the items into the given table
		 */
		fun into(table: Table) = PostIntoBuilder(table)
	}


	/**
	 * Builder-step with options for after the "into"-statement
	 */
	class PostIntoBuilder(
		private val target: Table
	) {

		/**
		 * Insert values into the given columns
		 */
		fun columns(columns: List<Column<*>>) = PostFieldsBuilder(target, columns)


		/**
		 * Insert values into the given columns
		 */
		fun columns(vararg columns: Column<*>) = PostFieldsBuilder(target, columns.toList())


		/**
		 * Insert values into all columns. Only use when inserting via a sub-query
		 */
		fun allColumns() = PostFieldsBuilder(target, listOf())

	}


	/**
	 * Builder-step with options for after specifying the columns
	 */
	class PostFieldsBuilder(
		private val target: Table,
		private val fields: List<Column<*>>
	) {

		/**
		 * Insert the result of the given sub-query
		 */
		fun query(query: QueryStatement<*>) = PostContentBuilder(target, fields, query)


		/**
		 * Insert the result of the given sub-query
		 */
		fun query(query: QueryBuilderEndStep<*>) = query(query.build<Any>())


		/**
		 * Insert the given items
		 */
		fun items(items: List<InsertItem>) = PostContentBuilder(target, fields, ItemsInsertContent(items))


		/**
		 * Insert the given items
		 */
		fun items(vararg items: InsertItem) = items(items.toList())

	}


	/**
	 * Builder-step with options for after the "values"-statement
	 */
	class PostContentBuilder(
		private val target: Table,
		private val fields: List<Column<*>>,
		private val content: InsertContent
	) : InsertBuilderEndStep {

		/**
		 * Build the [InsertStatement] with the current data
		 */
		override fun build() = InsertStatement(
			target = target,
			fields = fields,
			content = content,
			returning = null
		)


		/**
		 * Specify the columns of the inserted rows to return
		 */
		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, fields, content, ReturnColumns(columns))


		/**
		 * Specify the columns of the inserted rows to return
		 */
		fun returning(vararg columns: Column<*>) = returning(columns.toList())


		/**
		 * Return all columns of the inserted rows
		 */
		fun returningAll() = PostReturningBuilder(target, fields, content, ReturnAllColumns())

	}


	/**
	 * Builder-step with options for after the "returning"-statement
	 */
	class PostReturningBuilder(
		private val target: Table,
		private val fields: List<Column<*>>,
		private val content: InsertContent,
		private val returning: Returning
	) : InsertBuilderEndStep {

		/**
		 * Build the [InsertStatement] with the current data
		 */
		override fun build() = InsertStatement(
			target = target,
			fields = fields,
			content = content,
			returning = returning
		)

	}

}