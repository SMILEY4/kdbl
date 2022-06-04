package de.ruegnerlukas.sqldsl2.schema

import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr

open class Column(val parentTable: Table<*>, val columnName: String): ColumnExpr {

	private val constraints = mutableListOf<ColumnConstraint>()

	fun register(constraint: ColumnConstraint) {
		constraints.add(constraint)
	}

	fun getConstraints() = constraints.toList()

	inline fun <reified T> hasConstraint(): Boolean {
		return getConstraints().filterIsInstance<T>().isNotEmpty()
	}

	fun primaryKey(onConflict: OnConflict = OnConflict.ABORT): Column {
		return this.apply { register(PrimaryKeyConstraint(onConflict)) }
	}

	fun notNull(onConflict: OnConflict = OnConflict.ABORT): Column {
		return this.apply { register(NotNullConstraint(onConflict)) }
	}

	fun unique(onConflict: OnConflict = OnConflict.ABORT): Column {
		return this.apply { register(UniqueConstraint(onConflict)) }
	}

	fun autoIncrement(): Column {
		return this.apply { register(AutoIncrementPseudoConstraint()) }
	}


	fun foreignKey(table: Table<*>, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column {
		return this.apply { register(ForeignKeyConstraint(table, null, onDelete, onUpdate)) }
	}

	fun foreignKey(column: Column, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column {
		return this.apply { register(ForeignKeyConstraint(column.parentTable, column, onDelete, onUpdate)) }
	}

}

class IntColumn(parentTable: Table<*>, columnName: String) : Column(parentTable, columnName) {
	companion object {
		fun Table<*>.integer(name: String) = IntColumn(this, name).apply { register(this) }
	}
}

class FloatColumn(parentTable: Table<*>, columnName: String) : Column(parentTable, columnName) {
	companion object {
		fun Table<*>.float(name: String) = FloatColumn(this, name).apply { register(this) }
	}
}

class TextColumn(parentTable: Table<*>, columnName: String) : Column(parentTable, columnName) {
	companion object {
		fun Table<*>.text(name: String) = TextColumn(this, name).apply { register(this) }
	}
}

class BooleanColumn(parentTable: Table<*>, columnName: String) : Column(parentTable, columnName) {
	companion object {
		fun Table<*>.boolean(name: String) = BooleanColumn(this, name).apply { register(this) }
	}
}