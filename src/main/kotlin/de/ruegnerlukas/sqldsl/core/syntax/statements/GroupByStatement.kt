package de.ruegnerlukas.sqldsl.core.syntax.statements

import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef

class GroupByStatement(val columns: List<ColumnRef<*, *>>)