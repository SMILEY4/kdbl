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
import de.ruegnerlukas.sqldsl.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.where.WhereExpression
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement

fun query() = QueryBuilder()

interface QueryBuilderEndStep {
	fun build(): QueryStatement
}

class QueryBuilder {

	fun select(expressions: List<SelectExpression<*>>): PostSelectBuilder {
		return PostSelectBuilder(SelectStatement(expressions, false))
	}

	fun select(vararg expressions: SelectExpression<*>): PostSelectBuilder {
		return PostSelectBuilder(SelectStatement(expressions.toList(), false))
	}

	fun selectDistinct(expressions: List<SelectExpression<*>>): PostSelectBuilder {
		return PostSelectBuilder(SelectStatement(expressions, true))
	}

	fun selectDistinct(vararg expressions: SelectExpression<*>): PostSelectBuilder {
		return PostSelectBuilder(SelectStatement(expressions.toList(), false))
	}

}


class PostSelectBuilder(val select: SelectStatement) {

	fun from(expressions: List<FromExpression>): PostFromBuilder {
		return PostFromBuilder(select, FromStatement(expressions))
	}

	fun from(vararg expressions: FromExpression): PostFromBuilder {
		return PostFromBuilder(select, FromStatement(expressions.toList()))
	}

}


class PostFromBuilder(val select: SelectStatement, val from: FromStatement) : QueryBuilderEndStep {

	fun where(condition: WhereExpression): PostWhereBuilder {
		return PostWhereBuilder(select, from, WhereStatement(condition))
	}

	fun groupBy(expressions: List<GroupByExpression>): PostGroupByBuilder {
		return PostGroupByBuilder(select, from, null, GroupByStatement(expressions))
	}

	fun groupBy(vararg expressions: GroupByExpression): PostGroupByBuilder {
		return PostGroupByBuilder(select, from, null, GroupByStatement(expressions.toList()))
	}

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, null, null, null, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, null, null, null, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement {
		return QueryStatement(
			select = select,
			from = from
		)
	}

}


class PostWhereBuilder(val select: SelectStatement, val from: FromStatement, val where: WhereStatement) : QueryBuilderEndStep {

	fun groupBy(expressions: List<GroupByExpression>): PostGroupByBuilder {
		return PostGroupByBuilder(select, from, where, GroupByStatement(expressions))
	}

	fun groupBy(vararg expressions: GroupByExpression): PostGroupByBuilder {
		return PostGroupByBuilder(select, from, where, GroupByStatement(expressions.toList()))
	}

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, where, null, null, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, where, null, null, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement {
		return QueryStatement(
			select = select,
			from = from,
			where = where
		)
	}
}


class PostGroupByBuilder(val select: SelectStatement, val from: FromStatement, val where: WhereStatement?, val groupBy: GroupByStatement?) :
	QueryBuilderEndStep {

	fun having(condition: HavingExpression): PostHavingBuilder {
		return PostHavingBuilder(select, from, where, groupBy, HavingStatement(condition))
	}

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, where, groupBy, null, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, where, groupBy, null, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement {
		return QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy
		)
	}
}


class PostHavingBuilder(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement
) : QueryBuilderEndStep {

	fun orderBy(expressions: List<OrderByExpression>): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, where, groupBy, having, OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: OrderByExpression): PostOrderByBuilder {
		return PostOrderByBuilder(select, from, where, groupBy, having, OrderByStatement(expressions.toList()))
	}

	override fun build(): QueryStatement {
		return QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having
		)
	}
}


class PostOrderByBuilder(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement?,
	val orderBy: OrderByStatement
) : QueryBuilderEndStep {

	fun limit(limit: Int): PostLimitBuilder {
		return PostLimitBuilder(select, from, where, groupBy, having, orderBy, LimitStatement(0, null))
	}

	fun limit(limit: Int, offset: Int): PostLimitBuilder {
		return PostLimitBuilder(select, from, where, groupBy, having, orderBy, LimitStatement(0, offset))
	}

	override fun build(): QueryStatement {
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


class PostLimitBuilder(
	val select: SelectStatement,
	val from: FromStatement,
	val where: WhereStatement?,
	val groupBy: GroupByStatement?,
	val having: HavingStatement?,
	val orderBy: OrderByStatement?,
	val limit: LimitStatement
) : QueryBuilderEndStep {

	override fun build(): QueryStatement {
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


