package de.ruegnerlukas.sqldsl.core.grammar.refs

import de.ruegnerlukas.sqldsl.core.grammar.expression.LiteralValue

interface ColumnRef : LiteralValue {
    fun getColumnName(): String
    fun getTableName(): String
}