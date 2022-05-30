package de.ruegnerlukas.sqldsl.core.schema

import kotlin.reflect.KClass

class SqlValue<T>(private val value: T, private val type: KClass<*>) {

    fun asString(): String {
        @Suppress("UNCHECKED_CAST")
        return when (type) {
            Int::class -> value.toString()
            String::class -> "\"$value\""
            Boolean::class -> if ((this as SqlValue<Boolean>).value) "TRUE" else "FALSE"
            else -> ""
        }
    }

}