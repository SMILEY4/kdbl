package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.Table
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

		fun or(onConflict: OnConflict) = PostConflictBuilder(target, onConflict)

		fun orRollback() = or(OnConflict.ROLLBACK)
		fun orAbort() = or(OnConflict.ABORT)
		fun orFail() = or(OnConflict.FAIL)
		fun orIgnore() = or(OnConflict.IGNORE)
		fun orReplace() = or(OnConflict.REPLACE)

		fun columns(columns: List<Column<*>>) = PostFieldsBuilder(target, OnConflict.ABORT, columns)


	}


	class PostConflictBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
	) {

		fun columns(columns: List<Column<*>>) = PostFieldsBuilder(target, onConflict, columns)

		fun columns(vararg columns: Column<*>) = columns(columns.toList())

		fun allColumns() = columns(listOf())

	}


	class PostFieldsBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val fields: List<Column<*>>
	) {

		fun query(query: QueryStatement<*>) = PostContentBuilder(target, onConflict, fields, query)

		fun query(query: QueryBuilderEndStep<*>) = query(query.build<Any>())

		fun items(items: List<InsertItem>) = PostContentBuilder(target, onConflict, fields, ItemsInsertContent(items))

		fun items(vararg items: InsertItem) = items(items.toList())

	}


	class PostContentBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val fields: List<Column<*>>,
		private val content: InsertContent
	) : InsertBuilderEndStep {

		override fun build() = InsertStatement(
			onConflict = onConflict,
			target = target,
			fields = fields,
			content = content,
			returning = null
		)

		fun returning(columns: List<Column<*>>) = PostReturningBuilder(target, onConflict, fields, content, ReturnColumns(columns))

		fun returning(vararg columns: Column<*>) = returning(columns.toList())

		fun returningAll() = PostReturningBuilder(target, onConflict, fields, content, ReturnAllColumns())

	}


	class PostReturningBuilder(
		private val target: Table,
		private val onConflict: OnConflict,
		private val fields: List<Column<*>>,
		private val content: InsertContent,
		private val returning: Returning
	) : InsertBuilderEndStep {

		override fun build() = InsertStatement(
			onConflict = onConflict,
			target = target,
			fields = fields,
			content = content,
			returning = returning
		)

	}

}