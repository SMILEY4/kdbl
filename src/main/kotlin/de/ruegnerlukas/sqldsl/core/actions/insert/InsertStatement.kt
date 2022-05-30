package de.ruegnerlukas.sqldsl.core.actions.insert

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.OnConflict
import de.ruegnerlukas.sqldsl.core.schema.SqlValue
import de.ruegnerlukas.sqldsl.core.schema.Table

data class InsertStatement(
	val table: Table,
	val onConflict: OnConflict,
	val columns: List<Column<*,*>>,
	val items: List<Item>
) {

	class Item {

		private val values = mutableMapOf<Column<*,*>, SqlValue<*>>()

		fun set(column: Column<Int,*>, value: Int) {
			values[column] = SqlValue(value, Int::class)
		}

		fun set(column: Column<String,*>, value: String) {
			values[column] = SqlValue(value, String::class)
		}

		fun set(column: Column<Boolean,*>, value: Boolean) {
			values[column] = SqlValue(value, Boolean::class)
		}

		fun <T> get(column: Column<T,*>) = values[column]

	}

}
