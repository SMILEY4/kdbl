package de.ruegnerlukas.sqldsl.core.syntax.statements

import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef

class OrderByStatement(val entries: List<OrderByEntry>)


class OrderByEntry(val column: ColumnRef<*, *>, val dir: Dir)


enum class Dir {
	ASC,
	DESC
}