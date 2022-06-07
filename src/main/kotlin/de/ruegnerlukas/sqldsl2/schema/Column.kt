package de.ruegnerlukas.sqldsl2.schema

import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn

open class Column<T: AnyValueType>(private val parentTable: Table<*>, private val columnName: String) : QualifiedColumn<T> {

	private val constraints = mutableListOf<ColumnConstraint>()

	fun register(constraint: ColumnConstraint) {
		constraints.add(constraint)
	}

	fun getConstraints() = constraints.toList()

	override fun getColumnName() = columnName

	override fun getParentTable() = parentTable

	inline fun <reified T> hasConstraint(): Boolean {
		return getConstraints().filterIsInstance<T>().isNotEmpty()
	}

	fun primaryKey(onConflict: OnConflict = OnConflict.ABORT): Column<T> {
		return this.apply { register(PrimaryKeyConstraint(onConflict)) }
	}

	fun notNull(onConflict: OnConflict = OnConflict.ABORT): Column<T> {
		return this.apply { register(NotNullConstraint(onConflict)) }
	}

	fun unique(onConflict: OnConflict = OnConflict.ABORT): Column<T> {
		return this.apply { register(UniqueConstraint(onConflict)) }
	}

	fun autoIncrement(): Column<T> {
		return this.apply { register(AutoIncrementPseudoConstraint()) }
	}


	fun foreignKey(table: Table<*>, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column<T> {
		return this.apply { register(ForeignKeyConstraint(table, null, onDelete, onUpdate)) }
	}

	fun foreignKey(column: Column<*>, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column<T> {
		return this.apply { register(ForeignKeyConstraint(column.parentTable, column, onDelete, onUpdate)) }
	}


}

class IntColumn(parentTable: Table<*>, columnName: String) : Column<IntValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.integer(name: String) = IntColumn(this, name).apply { register(this) }
	}
}

class FloatColumn(parentTable: Table<*>, columnName: String) : Column<IntValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.float(name: String) = FloatColumn(this, name).apply { register(this) }
	}
}

class TextColumn(parentTable: Table<*>, columnName: String) : Column<IntValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.text(name: String) = TextColumn(this, name).apply { register(this) }
	}
}

class BooleanColumn(parentTable: Table<*>, columnName: String) : Column<IntValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.boolean(name: String) = BooleanColumn(this, name).apply { register(this) }
	}
}