package de.ruegnerlukas.sqldsl2.grammar.table

import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.DerivedColumn
import de.ruegnerlukas.sqldsl2.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.TableFromExpression


/**
 * Base interface for all table-like objects
 */
interface TableBase: TableFromExpression


/**
 * A standard table
 */
interface StandardTable : TableBase {
	val tableName: String
}


/**
 * A table with a different name
 */
interface AliasTable : TableBase {
	val baseTable: TableBase
	val aliasName: String
}


/**
 * A derived table, e.g. the result from a sub-query or a join
 */
class DerivedTable(val tableName: String) : TableBase {

	private var content: FromExpression? = null

	fun assign(content: FromExpression): DerivedTable {
		this.content = content
		return this
	}

	fun getContent(): FromExpression {
		return this.content ?: throw IllegalStateException("No content assigned to derived table '$tableName'")
	}

	fun column(columnName: String): DerivedColumn {
		return DerivedColumn(this, columnName)
	}


	fun column(columnExpr: ColumnExpr): DerivedColumn {
		return DerivedColumn(this, columnExpr.getColumnName())
	}

}


