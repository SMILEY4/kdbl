package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.schema.Table

object SQL {

	private val queryBuilder = QueryBuilder<AnyValueType>()
	private val insertBuilder = InsertBuilder()
	private val deleteBuilder = DeleteBuilder()
	private val createBuilder = CreateBuilder()

	fun select(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>) = queryBuilder.select(expressions)

	fun select(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>) = queryBuilder.select(expressions.toList())

	fun <T : AnyValueType> select(expression: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>) = QueryBuilder<T>().select(listOf(expression))

	fun selectAll() = queryBuilder.selectAll()

	fun selectDistinct(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>) = queryBuilder.selectDistinct(expressions)

	fun selectDistinct(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>) = queryBuilder.selectDistinct(expressions.toList())

	fun selectAllDistinct() = queryBuilder.selectAllDistinct()

	fun <T : AnyValueType> selectDistinct(expression: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>) = QueryBuilder<T>().selectDistinct(listOf(expression))

	fun insertInto(target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget) = insertBuilder.insertInto(target)

	fun deleteFrom(target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget) = deleteBuilder.deleteFrom(target)

	fun createTable(table: Table<*>) = createBuilder.create(table)

}