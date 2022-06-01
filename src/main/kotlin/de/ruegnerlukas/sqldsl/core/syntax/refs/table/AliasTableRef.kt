package de.ruegnerlukas.sqldsl.core.syntax.refs.table

import de.ruegnerlukas.sqldsl.core.schema.Table

class AliasTableRef<T : Table>(val table: T, val alias: String) : TableRef

fun <T : Table> T.alias(alias: String): AliasTableRef<T> {
	return AliasTableRef(this, alias)
}