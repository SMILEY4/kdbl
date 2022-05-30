package de.ruegnerlukas.sqldsl.core.schema

import de.ruegnerlukas.sqldsl.core.actions.query.ColumnRef


open class Column<D, T : Table>(
	private val parentTable: T,
	private val name: String,
	private val type: DataType
) : ColumnRef<D, T> {

	private val constraints = mutableListOf<ColumnConstraint>()

	fun register(constraint: ColumnConstraint) {
		constraints.add(constraint)
	}

	fun getName() = name

	fun getDataType() = type

	fun getParentTable() = parentTable

	fun getConstraints() = constraints.toList()

	inline fun <reified T> hasConstraint(): Boolean {
		return getConstraints().filterIsInstance<T>().isNotEmpty()
	}

	override fun getTableRef() = parentTable

	override fun getColumn() = this

}


fun <T : Table> T.integer(name: String): Column<Int, T> {
	return Column<Int, T>(this, name, DataType.INTEGER).apply { register(this) }
}

fun <T : Table> T.text(name: String): Column<String, T> {
	return Column<String, T>(this, name, DataType.TEXT).apply { register(this) }
}

fun <T : Table> T.boolean(name: String): Column<Boolean, T> {
	return Column<Boolean, T>(this, name, DataType.BOOLEAN).apply { register(this) }
}


fun <D, T : Table> Column<D, T>.primaryKey(onConflict: OnConflict = OnConflict.ABORT): Column<D, T> {
	return this.apply { register(PrimaryKeyConstraint(onConflict)) }
}

fun <T : Table> Column<Int, T>.autoIncrement(): Column<Int, T> {
	return this.apply { register(AutoIncrementPseudoConstraint()) }
}

fun <T : Table, D> Column<D, T>.notNull(onConflict: OnConflict = OnConflict.ABORT): Column<D, T> {
	return this.apply { register(NotNullConstraint(onConflict)) }
}

fun <T : Table, D> Column<D, T>.unique(onConflict: OnConflict = OnConflict.ABORT): Column<D, T> {
	return this.apply { register(UniqueConstraint(onConflict)) }
}

fun <T : Table, D> Column<D, T>.foreignKey(
	table: Table,
	onDelete: OnDelete = OnDelete.NO_ACTION,
	onUpdate: OnUpdate = OnUpdate.NO_ACTION
): Column<D, T> {
	return this.apply { register(ForeignKeyConstraint(table, null, onDelete, onUpdate)) }
}

fun <T : Table, D> Column<D, T>.foreignKey(
	column: Column<*, *>,
	onDelete: OnDelete = OnDelete.NO_ACTION,
	onUpdate: OnUpdate = OnUpdate.NO_ACTION
): Column<D, T> {
	return this.apply { register(ForeignKeyConstraint(column.getParentTable(), column, onDelete, onUpdate)) }
}

fun <T : Table> Column<Int, T>.default(value: Int): Column<Int, T> {
	return this.apply { register(DefaultValueConstraint(SqlValue(value, Int::class))) }
}

fun <T : Table> Column<String, T>.default(value: String): Column<String, T> {
	return this.apply { register(DefaultValueConstraint(SqlValue(value, String::class))) }
}

fun <T : Table> Column<Boolean, T>.default(value: Boolean): Column<Boolean, T> {
	return this.apply { register(DefaultValueConstraint(SqlValue(value, Boolean::class))) }
}
