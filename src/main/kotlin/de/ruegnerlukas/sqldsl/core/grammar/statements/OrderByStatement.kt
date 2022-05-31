package de.ruegnerlukas.sqldsl.core.grammar.statements

import de.ruegnerlukas.sqldsl.core.grammar.refs.ColumnRef

class OrderByStatement(val entries: List<OrderByEntry>)



class OrderByEntry(val column: ColumnRef, val dir: Dir)



enum class Dir {
	ASC,
	DESC
}