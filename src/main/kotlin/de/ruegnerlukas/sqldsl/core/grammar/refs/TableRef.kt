package de.ruegnerlukas.sqldsl.core.grammar.refs

import de.ruegnerlukas.sqldsl.core.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.core.schema.Table

interface TableRef : FromExpression

interface DirectTableRef : TableRef

class AliasTableRef<T : Table>(val table: T, val alias: String) : TableRef

fun <T : Table> T.alias(alias: String): AliasTableRef<T> {
	return AliasTableRef(this, alias)
}
