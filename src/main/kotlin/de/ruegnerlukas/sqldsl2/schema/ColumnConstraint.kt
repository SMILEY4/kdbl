package de.ruegnerlukas.sqldsl2.schema

open class ColumnConstraint

class PrimaryKeyConstraint(val onConflict: OnConflict) : ColumnConstraint()

class AutoIncrementPseudoConstraint : ColumnConstraint()

class NotNullConstraint(val onConflict: OnConflict) : ColumnConstraint()

class UniqueConstraint(val onConflict: OnConflict) : ColumnConstraint()

class ForeignKeyConstraint(val table: Table<*>, val column: Column<*>?, val onDelete: OnDelete, val onUpdate: OnUpdate) : ColumnConstraint()

class DefaultIntValueConstraint(private val value: Int) : ColumnConstraint() {
    fun getValueAsString() = value.toString()
}

class DefaultFloatValueConstraint(private val value: Float) : ColumnConstraint() {
	fun getValueAsString() = value.toString()
}

class DefaultStringValueConstraint(private val value: String) : ColumnConstraint() {
	fun getValueAsString() = value.toString()
}

class DefaultBooleanValueConstraint(private val value: Boolean) : ColumnConstraint() {
	fun getValueAsString() = value.toString()
}


