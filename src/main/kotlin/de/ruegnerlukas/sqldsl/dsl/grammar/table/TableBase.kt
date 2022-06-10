package de.ruegnerlukas.sqldsl.dsl.grammar.table

import de.ruegnerlukas.sqldsl.dsl.builders.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.BooleanValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.FloatValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.IntValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.StringValueType


/**
 * Base interface for all table-like objects
 */
interface TableBase: de.ruegnerlukas.sqldsl.dsl.grammar.from.TableFromExpression


/**
 * A standard table
 */
interface StandardTable : de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase, de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget {
	val tableName: String
}


/**
 * A table with a different name
 */
interface AliasTable : de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase {
	val baseTable: de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase
	val aliasName: String
}


/**
 * A derived table, e.g. the result from a sub-query or a join
 */
class DerivedTable(val tableName: String) : de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase {

	private var content: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression? = null

	fun assign(content: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression): de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable {
		this.content = content
		return this
	}

	fun assign(content: QueryBuilderEndStep<*>): de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable {
		this.content = content.build()
		return this
	}

	fun getContent(): de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression {
		return this.content ?: throw IllegalStateException("No content assigned to derived table '$tableName'")
	}

	fun columnInt(columnName: String): de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn<IntValueType> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn(this, columnName)
	}

	fun columnFloat(columnName: String): de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn<FloatValueType> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn(this, columnName)
	}

	fun columnString(columnName: String): de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn<StringValueType> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn(this, columnName)
	}
	fun columnBool(columnName: String): de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn<BooleanValueType> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn(this, columnName)
	}


	fun <T: AnyValueType> column(columnExpr: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<T>): de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.expr.DerivedColumn(this, columnExpr.getColumnName())
	}

}


