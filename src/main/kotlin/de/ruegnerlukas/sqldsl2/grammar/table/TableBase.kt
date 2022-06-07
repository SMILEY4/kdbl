package de.ruegnerlukas.sqldsl2.grammar.table

import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.DerivedColumn
import de.ruegnerlukas.sqldsl2.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.TableFromExpression
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.schema.AnyValueType
import de.ruegnerlukas.sqldsl2.schema.BooleanValueType
import de.ruegnerlukas.sqldsl2.schema.FloatValueType
import de.ruegnerlukas.sqldsl2.schema.IntValueType
import de.ruegnerlukas.sqldsl2.schema.StringValueType


/**
 * Base interface for all table-like objects
 */
interface TableBase: TableFromExpression


/**
 * A standard table
 */
interface StandardTable : TableBase, CommonTarget {
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

	fun columnInt(columnName: String): DerivedColumn<IntValueType> {
		return DerivedColumn(this, columnName)
	}

	fun columnFloat(columnName: String): DerivedColumn<FloatValueType> {
		return DerivedColumn(this, columnName)
	}

	fun columnString(columnName: String): DerivedColumn<StringValueType> {
		return DerivedColumn(this, columnName)
	}
	fun columnBool(columnName: String): DerivedColumn<BooleanValueType> {
		return DerivedColumn(this, columnName)
	}



	fun <T: AnyValueType> columnInt(columnExpr: ColumnExpr<T>): DerivedColumn<T> {
		return DerivedColumn(this, columnExpr.getColumnName())
	}

}


