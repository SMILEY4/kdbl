package de.ruegnerlukas.sqldsl.core.schema

open class Column<T>(private val parentTable: Table, private val name: String, private val type: DataType) {

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

}




fun Table.integer(name: String): Column<Int> {
    return Column<Int>(this, name, DataType.INTEGER).apply { register(this) }
}

fun Table.text(name: String): Column<String> {
    return Column<String>(this, name, DataType.TEXT).apply { register(this) }
}

fun Table.boolean(name: String): Column<Boolean> {
    return Column<Boolean>(this, name, DataType.BOOLEAN).apply { register(this) }
}


fun <T> Column<T>.primaryKey(onConflict: OnConflict = OnConflict.ABORT): Column<T> {
    return this.apply { register(PrimaryKeyConstraint(onConflict)) }
}

fun Column<Int>.autoIncrement(): Column<Int> {
    return this.apply { register(AutoIncrementPseudoConstraint()) }
}

fun <T> Column<T>.notNull(onConflict: OnConflict = OnConflict.ABORT): Column<T> {
    return this.apply { register(NotNullConstraint(onConflict)) }
}

fun <T> Column<T>.unique(onConflict: OnConflict = OnConflict.ABORT): Column<T> {
    return this.apply { register(UniqueConstraint(onConflict)) }
}

fun <T> Column<T>.foreignKey(table: Table, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column<T> {
    return this.apply { register(ForeignKeyConstraint(table, null, onDelete, onUpdate)) }
}

fun <T> Column<T>.foreignKey(column: Column<*>, onDelete: OnDelete = OnDelete.NO_ACTION, onUpdate: OnUpdate = OnUpdate.NO_ACTION): Column<T> {
    return this.apply { register(ForeignKeyConstraint(column.getParentTable(), column, onDelete, onUpdate)) }
}

fun Column<Int>.default(value: Int): Column<Int> {
    return this.apply { register(DefaultValueConstraint(SqlValue(value, Int::class))) }
}

fun Column<String>.default(value: String): Column<String> {
    return this.apply { register(DefaultValueConstraint(SqlValue(value, String::class))) }
}

fun Column<Boolean>.default(value: Boolean): Column<Boolean> {
    return this.apply { register(DefaultValueConstraint(SqlValue(value, Boolean::class))) }
}
