package de.ruegnerlukas.sqldsl.core.actions.select

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table


fun query(block: SelectBuilder.QueryExpression.() -> Unit) {
	val expr = SelectBuilder.QueryExpression().apply(block)
}


object SelectBuilder {

	class QueryExpression {

		fun <T : Table> table(table: T) = table(table, table.getTableName())

		fun <T : Table> table(table: T, alias: String): SourceRef<T> {
			return SourceRef(table, alias)
		}

		fun from(vararg refs: SourceRef<*>) {}

		fun select(vararg refs: SelectRef<*>) {}

	}


	class SourceRef<T : Table>(private val table: T, private val alias: String) {

		fun <D> column(column: Column<D>) = column(column, column.getName())

		fun <D> column(column: Column<D>, alias: String): SelectRef<D> {
			return SelectRef(column, alias)
		}
	}


	class SelectRef<T>(val column: Column<T>, val alias: String) {

	}


	class FromExpression {
	}


	class SelectExpression {
	}


}


