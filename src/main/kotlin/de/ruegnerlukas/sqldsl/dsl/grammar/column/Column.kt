package de.ruegnerlukas.sqldsl.dsl.grammar.column

import de.ruegnerlukas.sqldsl.dsl.schema.Table

open class Column<T : AnyValueType>(
	private val parentTable: Table<*>,
	private val columnName: String,
	private val columnType: ColumnType
) : de.ruegnerlukas.sqldsl.dsl.grammar.expr.QualifiedColumn<T> {

	private val constraints = mutableListOf<ColumnConstraint>()

	fun register(constraint: ColumnConstraint) {
		constraints.add(constraint)
	}

	fun getConstraints() = constraints.toList()

	inline fun <reified T: ColumnConstraint> getConstraint(): T? {
		return getConstraints().filterIsInstance<T>().firstOrNull()
	}

	fun getColumnType() = columnType

	override fun getColumnName() = columnName

	override fun getParentTable() = parentTable

	inline fun <reified T: ColumnConstraint> hasConstraint(): Boolean {
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

	fun foreignKey(table: Table<*>, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column<T> {
		return this.apply { register(ForeignKeyConstraint(table, null, onDelete, onUpdate)) }
	}

	fun foreignKey(column: Column<*>, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column<T> {
		return this.apply { register(ForeignKeyConstraint(column.parentTable, column, onDelete, onUpdate)) }
	}

}


class IntColumn(parentTable: Table<*>, columnName: String) : Column<IntValueType>(parentTable, columnName, ColumnType.INT) {
	companion object {
		fun Table<*>.integer(name: String) = IntColumn(this, name).apply { register(this) }

		fun Column<IntValueType>.default(value: Int): Column<IntValueType> {
			return this.apply { register(DefaultIntValueConstraint(value)) }
		}

		fun Column<IntValueType>.autoIncrement(): Column<IntValueType> {
			return this.apply { register(AutoIncrementPseudoConstraint()) } as IntColumn
		}

	}
}

class FloatColumn(parentTable: Table<*>, columnName: String) : Column<FloatValueType>(parentTable, columnName, ColumnType.INT) {
	companion object {
		fun Table<*>.float(name: String) = FloatColumn(this, name).apply { register(this) }

		fun Column<FloatValueType>.default(value: Float): Column<FloatValueType> {
			return this.apply { register(DefaultFloatValueConstraint(value)) }
		}
	}
}

class TextColumn(parentTable: Table<*>, columnName: String) : Column<StringValueType>(parentTable, columnName, ColumnType.TEXT) {
	companion object {
		fun Table<*>.text(name: String) = TextColumn(this, name).apply { register(this) }

		fun Column<StringValueType>.default(value: String): Column<StringValueType> {
			return this.apply { register(DefaultStringValueConstraint(value)) }
		}
	}

}

class BooleanColumn(parentTable: Table<*>, columnName: String) : Column<BooleanValueType>(parentTable, columnName, ColumnType.BOOLEAN) {
	companion object {
		fun Table<*>.boolean(name: String) = BooleanColumn(this, name).apply { register(this) }

		fun Column<BooleanValueType>.default(value: Boolean): Column<BooleanValueType> {
			return this.apply { register(DefaultBooleanValueConstraint(value)) }
		}
	}

}