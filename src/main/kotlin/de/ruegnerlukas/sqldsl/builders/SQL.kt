package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.Table

object SQL {

	private val queryBuilder = QueryBuilder<AnyValueType>()
	private val insertBuilder = InsertBuilder()

	fun select(expressions: List<SelectExpression<*>>) = queryBuilder.select(expressions)

	fun select(vararg expressions: SelectExpression<*>) = queryBuilder.select(expressions.toList())

	fun <T: AnyValueType>select(expression: SelectExpression<T>) = QueryBuilder<T>().select(listOf(expression))

	fun selectAll() = queryBuilder.selectAll()

	fun selectDistinct(expressions: List<SelectExpression<*>>) = queryBuilder.selectDistinct(expressions)

	fun selectDistinct(vararg expressions: SelectExpression<*>) = queryBuilder.selectDistinct(expressions.toList())

	fun selectAllDistinct() = queryBuilder.selectAllDistinct()

	fun <T: AnyValueType>selectDistinct(expression: SelectExpression<T>) = QueryBuilder<T>().selectDistinct(listOf(expression))


	fun insertInto(target: CommonTarget) = insertBuilder.insertInto(target)


	fun createTable(table: Table<*>): Nothing = TODO()

}