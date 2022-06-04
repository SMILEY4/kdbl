package de.ruegnerlukas.sqldsl2.schema

open class ColumnConstraint

class PrimaryKeyConstraint(val onConflict: OnConflict) : ColumnConstraint()

class AutoIncrementPseudoConstraint : ColumnConstraint()

class NotNullConstraint(val onConflict: OnConflict) : ColumnConstraint()

class UniqueConstraint(val onConflict: OnConflict) : ColumnConstraint()

class ForeignKeyConstraint(val table: Table<*>, val column: Column?, val onDelete: OnDelete, val onUpdate: OnUpdate) : ColumnConstraint()

//class DefaultValueConstraint<T>(private val value: SqlValue<T>) : ColumnConstraint() {
//    fun getValueAsString() = value.asString()
//}


