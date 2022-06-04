package de.ruegnerlukas.sqldsl2.grammar

import de.ruegnerlukas.sqldsl2.grammar.from.TableAliasFromExpression


interface TableLike

interface TableAlias : TableLike, TableAliasFromExpression {
	val alias: String
	val tableName: String
}
