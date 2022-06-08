package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByExpression
import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl.grammar.having.HavingExpression
import de.ruegnerlukas.sqldsl.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl.grammar.limit.LimitStatement
import de.ruegnerlukas.sqldsl.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.where.WhereExpression
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl.schema.AnyValueType


interface QueryBuilderEndStep<T: AnyValueType> {
	fun build(): QueryStatement<T>
}

open class QueryBuilder<T: AnyValueType> {

	fun select(expressions: List<SelectExpression<*>>): PostSelectBuilder<T> {
		return PostSelectBuilder(SelectStatement(expressions, false))
	}

	fun select(vararg expressions: SelectExpression<*>): PostSelectBuilder<T> {
		return PostSelectBuilder(SelectStatement(expressions.toList(), false))
	}

	fun selectAll(): PostSelectBuilder<T> {
		return PostSelectBuilder(SelectStatement(listOf(AllSelectExpression()), false))
	}

	fun selectDistinct(expressions: List<SelectExpression<*>>): PostSelectBuilder<T> {
		return PostSelectBuilder(SelectStatement(expressions, true))
	}

	fun selectDistinct(vararg expressions: SelectExpression<*>): PostSelectBuilder<T> {
		return PostSelectBuilder(SelectStatement(expressions.toList(), false))
	}

	fun selectAllDistinct(): PostSelectBuilder<T> {
		return PostSelectBuilder(SelectStatement(listOf(AllSelectExpression()), true))
	}

}


class PostSelectBuilder<T: AnyValueType>(val select: SelectStatement) {

	fun from(expressions: List<FromExpression>): PostFromBuilder<T> {
		return PostFromBuilder(select, FromStatement(expressions))
	}

	fun from(vararg expressions: FromExpression): PostFromBuilder<T> {
		return PostFromBuilder(select, FromStatement(expressions.toList()))
	}

}


class PostFromBuilder<T: AnyValueType>(val select: SelectStatement, val from: FromStatement) : QueryBuilderEndStep<T> {

	fun where(condition: WhereExpression): PostWhereBuilder<T> {
		return PostWhereBuilder(select, from, WhereStatement(condition))
	}

	fun groupBy(expressions: List<GroupByExpression>): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, null, GroupByStatement(expressions))
	}

	fun groupBy(vararg expressions: GroupByExpression): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, null, GroupByStatement(expressions.toList()))
	}

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, null, null, null, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, null, null, null, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement<T> {
		return QueryStatement(
			select = select,
			from = from
		)
	}

}


class PostWhereBuilder<T: AnyValueType>(val select: SelectStatement, val from: FromStatement, val where: WhereStatement) : QueryBuilderEndStep<T> {

	fun groupBy(expressions: List<GroupByExpression>): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, where, GroupByStatement(expressions))
	}

	fun groupBy(vararg expressions: GroupByExpression): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, where, GroupByStatement(expressions.toList()))
	}

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, null, null, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, null, null, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement<T> {
		return QueryStatement(
			select = select,
			from = from,
			where = where
		)
	}
}


class PostGroupByBuilder<T: AnyValueType>(val select: SelectStatement, val from: FromStatement, val where: WhereStatement?, val groupBy: GroupByStatement?) :
	QueryBuilderEndStep<T> {

	fun having(condition: HavingExpression): PostHavingBuilder<T> {
		return PostHavingBuilder(select, from, where, groupBy, HavingStatement(condition))
	}

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, null, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, null, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement<T> {
		return QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy
		)
	}
}


class PostHavingBuilder<T: AnyValueType>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement
) : QueryBuilderEndStep<T> {

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, having, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, having, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement<T> {
		return QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having
		)
	}
}


class PostOrderByBuilder<T: AnyValueType>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement?,
	val orderBy: OrderByStatement
) : QueryBuilderEndStep<T> {

	fun limit(limit: Int): PostLimitBuilder<T> {
		return PostLimitBuilder(select, from, where, groupBy, having, orderBy, LimitStatement(limit, null))
	}

	fun limit(limit: Int, offset: Int): PostLimitBuilder<T> {
		return PostLimitBuilder(select, from, where, groupBy, having, orderBy, LimitStatement(limit, offset))
	}

	override fun build(): QueryStatement<T> {
		return QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having,
			orderBy = orderBy
		)
	}

}


class PostLimitBuilder<T: AnyValueType>(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement?,
	val orderBy: OrderByStatement?,
	val limit: LimitStatement
) : QueryBuilderEndStep<T> {

	override fun build(): QueryStatement<T> {
		return QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having,
			orderBy = orderBy,
			limit = limit
		)
	}

}


