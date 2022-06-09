package de.ruegnerlukas.sqldsl.schema

interface ColumnConstraint

class PrimaryKeyConstraint(val onConflict: OnConflict) : ColumnConstraint

class AutoIncrementPseudoConstraint : ColumnConstraint

class NotNullConstraint(val onConflict: OnConflict) : ColumnConstraint

class UniqueConstraint(val onConflict: OnConflict) : ColumnConstraint

class ForeignKeyConstraint(val table: Table<*>, val column: Column<*>?, val onDelete: OnDelete, val onUpdate: OnUpdate) : ColumnConstraint

interface DefaultValueConstraint<T> : ColumnConstraint {
	fun getDefaultValue(): T
}

class DefaultIntValueConstraint(private val value: Int) : DefaultValueConstraint<Int> {
    override fun getDefaultValue() = value
}

class DefaultFloatValueConstraint(private val value: Float) : DefaultValueConstraint<Float> {
	override fun getDefaultValue() = value
}

class DefaultStringValueConstraint(private val value: String) : DefaultValueConstraint<String> {
	override fun getDefaultValue() = value
}

class DefaultBooleanValueConstraint(private val value: Boolean) : DefaultValueConstraint<Boolean> {
	override fun getDefaultValue() = value
}


