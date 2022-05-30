package de.ruegnerlukas.sqldsl.core.actions.insert

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.OnConflict
import de.ruegnerlukas.sqldsl.core.schema.Table


fun insertInto() = InsertInto.start()

fun insertInto(table: Table) = InsertInto.start(table)


object InsertInto {

	fun start() = TableStep()
	fun start(table: Table) = ColumnsStep(table)


	class TableStep {
		fun table(table: Table) = ColumnsStep(table)
	}


	class ColumnsStep(private val table: Table) {
		private var onConflict = OnConflict.ABORT

		fun or(onConflict: OnConflict) = this.apply { this.onConflict = onConflict }
		fun orAbort() = or(OnConflict.ABORT)
		fun orFail() = or(OnConflict.FAIL)
		fun orIgnore() = or(OnConflict.IGNORE)
		fun orReplace() = or(OnConflict.REPLACE)
		fun orRollback() = or(OnConflict.ROLLBACK)
		fun orDoNothing() = or(OnConflict.IGNORE)
		fun orDoUpdate() = or(OnConflict.REPLACE)

		fun columns(vararg columns: Column<*>) = ItemsStep(table, onConflict, columns.toList())
		fun columns(columns: List<Column<*>>) = ItemsStep(table, onConflict, columns)
		fun columnsAll() = ItemsStep(table, onConflict, table.getTableColumns())
	}


	class ItemsStep(private val table: Table, private val onConflict: OnConflict, private val columns: List<Column<*>>) {
		private val items = mutableListOf<InsertStatement.Item>()

		fun item(item: InsertStatement.Item) = this.apply { items.add(item) }
		fun item(items: List<InsertStatement.Item>) = this.apply { this.items.addAll(items) }
		fun item(block: InsertStatement.Item.() -> Unit) = this.apply { items.add(InsertStatement.Item().apply(block)) }

		fun build() = InsertStatement(table, onConflict, columns, items)
	}


}
