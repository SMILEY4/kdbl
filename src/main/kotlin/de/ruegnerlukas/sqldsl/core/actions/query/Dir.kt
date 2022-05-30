package de.ruegnerlukas.sqldsl.core.actions.query

enum class Dir {
	ASC,
	DESC
}


infix fun ColumnRef<*, *>.order(dir: Dir): Pair<ColumnRef<*, *>, Dir> {
	return this to dir
}

