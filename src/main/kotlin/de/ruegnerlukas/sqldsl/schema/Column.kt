package de.ruegnerlukas.sqldsl.schema

import de.ruegnerlukas.sqldsl.grammar.expr.QualifiedColumn

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

	fun default(value: Int): IntColumn {
		return this.apply { register(DefaultIntValueConstraint(value)) }
	}

}

class FloatColumn(parentTable: Table<*>, columnName: String) : Column<FloatValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.float(name: String) = FloatColumn(this, name).apply { register(this) }
	}

	fun default(value: Float): FloatColumn {
		return this.apply { register(DefaultFloatValueConstraint(value)) }
	}
}

class TextColumn(parentTable: Table<*>, columnName: String) : Column<StringValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.text(name: String) = TextColumn(this, name).apply { register(this) }
	}

	fun default(value: String): TextColumn {
		return this.apply { register(DefaultStringValueConstraint(value)) }
	}
}

class BooleanColumn(parentTable: Table<*>, columnName: String) : Column<BooleanValueType>(parentTable, columnName) {
	companion object {
		fun Table<*>.boolean(name: String) = BooleanColumn(this, name).apply { register(this) }
	}

	fun default(value: Boolean): BooleanColumn {
		return this.apply { register(DefaultBooleanValueConstraint(value)) }
	}
}