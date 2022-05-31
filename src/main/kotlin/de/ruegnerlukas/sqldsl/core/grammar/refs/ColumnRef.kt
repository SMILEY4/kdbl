package de.ruegnerlukas.sqldsl.core.grammar.refs

import de.ruegnerlukas.sqldsl.core.grammar.expression.LiteralValue
import de.ruegnerlukas.sqldsl.core.schema.Column

interface ColumnRef : LiteralValue {
    fun getColumnName(): String
    fun getTableName(): String
}


// TODO: temp
class ColumnRefImpl(private val column: Column<*,*>): ColumnRef {
    override fun getColumnName() = column.getColumnName()
    override fun getTableName() = column.getParentTable().getTableName()
}

fun ref(column: Column<*, *>) = ColumnRefImpl(column)