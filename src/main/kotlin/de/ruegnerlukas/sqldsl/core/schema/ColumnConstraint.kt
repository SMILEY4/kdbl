package de.ruegnerlukas.sqldsl.core.schema

import kotlin.reflect.KClass

open class ColumnConstraint

class PrimaryKeyConstraint(val onConflict: OnConflict) : ColumnConstraint()

class AutoIncrementPseudoConstraint : ColumnConstraint()

class NotNullConstraint(val onConflict: OnConflict) : ColumnConstraint()

class UniqueConstraint(val onConflict: OnConflict) : ColumnConstraint()

class ForeignKeyConstraint(val table: Table, val column: Column<*>?, val onDelete: OnDelete, val onUpdate: OnUpdate) : ColumnConstraint()

class DefaultValueConstraint<T>(private val value: T, private val type: KClass<*>) : ColumnConstraint() {
    fun getValueAsString(): String {
        @Suppress("UNCHECKED_CAST")
        return when (type) {
            Int::class -> value.toString()
            String::class -> "\"$value\""
            Boolean::class -> if ((this as DefaultValueConstraint<Boolean>).value) "TRUE" else "FALSE"
            else -> ""
        }
    }
}


