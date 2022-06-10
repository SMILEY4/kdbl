package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.BooleanValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.FloatValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.IntValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.StringValueType

fun de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression.assign(derivedTable: de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable): de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable {
	derivedTable.assign(this)
	return derivedTable
}

fun QueryBuilderEndStep<*>.assign(derivedTable: de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable): de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression {
	val query = this.build()
	derivedTable.assign(query)
	return derivedTable
}

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>.assign(column: de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn<T>): de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn<T> {
	return column.assign(this)
}

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>.alias(alias: String): de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn<T> {
	return de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn(this, alias)
}

fun QueryBuilderEndStep<*>.castInt() = this as QueryBuilderEndStep<IntValueType>
fun QueryBuilderEndStep<*>.castFloat() = this as QueryBuilderEndStep<FloatValueType>
fun QueryBuilderEndStep<*>.castString() = this as QueryBuilderEndStep<StringValueType>
fun QueryBuilderEndStep<*>.castBoolean() = this as QueryBuilderEndStep<BooleanValueType>

fun de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>.castInt() = this as de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<IntValueType>
fun de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>.castFloat() = this as de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<FloatValueType>
fun de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>.castString() = this as de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<StringValueType>
fun de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>.castBoolean() = this as de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<BooleanValueType>