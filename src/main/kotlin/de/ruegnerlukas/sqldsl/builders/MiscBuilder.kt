package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.BooleanValueType
import de.ruegnerlukas.sqldsl.schema.FloatValueType
import de.ruegnerlukas.sqldsl.schema.IntValueType
import de.ruegnerlukas.sqldsl.schema.StringValueType

fun FromExpression.assign(derivedTable: DerivedTable): DerivedTable {
	derivedTable.assign(this)
	return derivedTable
}

fun QueryBuilderEndStep<*>.assign(derivedTable: DerivedTable): FromExpression {
	val query = this.build()
	derivedTable.assign(query)
	return derivedTable
}

fun <T : AnyValueType> SelectExpression<T>.assign(column: AliasColumn<T>): AliasColumn<T> {
	return column.assign(this)
}

fun <T : AnyValueType> SelectExpression<T>.alias(alias: String): AliasColumn<T> {
	return AliasColumn(this, alias)
}

fun QueryBuilderEndStep<*>.castInt() = this as QueryBuilderEndStep<IntValueType>
fun QueryBuilderEndStep<*>.castFloat() = this as QueryBuilderEndStep<FloatValueType>
fun QueryBuilderEndStep<*>.castString() = this as QueryBuilderEndStep<StringValueType>
fun QueryBuilderEndStep<*>.castBoolean() = this as QueryBuilderEndStep<BooleanValueType>

fun QueryStatement<*>.castInt() = this as QueryStatement<IntValueType>
fun QueryStatement<*>.castFloat() = this as QueryStatement<FloatValueType>
fun QueryStatement<*>.castString() = this as QueryStatement<StringValueType>
fun QueryStatement<*>.castBoolean() = this as QueryStatement<BooleanValueType>