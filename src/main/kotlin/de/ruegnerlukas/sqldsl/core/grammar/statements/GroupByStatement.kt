package de.ruegnerlukas.sqldsl.core.grammar.statements

import de.ruegnerlukas.sqldsl.core.grammar.refs.column.ColumnRef

class GroupByStatement(val columns: List<ColumnRef<*, *>>)