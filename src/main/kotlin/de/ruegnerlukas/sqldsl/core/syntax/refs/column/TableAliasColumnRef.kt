package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.syntax.refs.table.AliasTableRef
import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table

class TableAliasColumnRef<D, T : Table>(val tableRef: AliasTableRef<T>, val column: Column<D, T>) : ColumnRef<D, T>

operator fun <D, T : Table> AliasTableRef<T>.get(column: Column<D, T>) = TableAliasColumnRef(this, column)
