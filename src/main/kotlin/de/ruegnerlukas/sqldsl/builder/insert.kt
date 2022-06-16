package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expression.Column
import de.ruegnerlukas.sqldsl.dsl.expression.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expression.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expression.Returning
import de.ruegnerlukas.sqldsl.dsl.expression.Table
import de.ruegnerlukas.sqldsl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.InsertContent
import de.ruegnerlukas.sqldsl.dsl.statements.InsertItem
import de.ruegnerlukas.sqldsl.dsl.statements.InsertItemImpl
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.ItemsInsertContent
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement

object InsertBuilder {

	class InsertItemBuilder {

		fun item() = InsertItemImpl()

	}

	class StartInsertBuilder {

		fun insert() = PostInsertBuilder()

	}

	class PostInsertBuilder {

		fun into(table: Table) = PostIntoBuilder(table)

	}


	class PostIntoBuilder(
		private val target: Table
	) {

		fun columns(columns: List<Column<*>>) = PostFieldsBuilder(target, columns)

		fun columns(vararg columns: Column<*>) = PostFieldsBuilder(target, columns.toList())

		fun allColumns() = PostFieldsBuilder(target, listOf())

	}

	class PostFieldsBuilder(
		private val target: Table,
		private val fields: List<Column<*>>
	) {

		fun query(query: QueryStatement<*>) = PostContentBuilder(target, fields, query)

		fun query(query: QueryBuilderEndStep<*>) = query(query.build<Any>())

		fun items(items: List<InsertItem>) = PostContentBuilder(target, fields, ItemsInsertContent(items))

		fun items(vararg items: InsertItem) = items(items.toList())

	}


	class PostContentBuilder(
		private val target: Table,
		private val fields: List<Column<*>>,
		private val content: InsertContent
	) : InsertBuilderEndStep {

		override fun build() = InsertStatement(
			target = target,
			fields = fields,
			content = content,
			returning = null
		)

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, fields, content, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, fields, content, ReturnAllColumns())

	}


	class PostReturningBuilder(
		private val target: Table,
		private val fields: List<Column<*>>,
		private val content: InsertContent,
		private val returning: Returning
	) : InsertBuilderEndStep {

		override fun build() = InsertStatement(
			target = target,
			fields = fields,
			content = content,
			returning = returning
		)

	}

}