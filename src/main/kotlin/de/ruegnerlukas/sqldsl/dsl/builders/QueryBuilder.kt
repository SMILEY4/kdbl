package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType


interface QueryBuilderEndStep<T: AnyValueType> {
	fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>
}

open class QueryBuilder<T: AnyValueType> {

	fun select(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>): PostSelectBuilder<T> {
		return PostSelectBuilder(de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(expressions, false))
	}

	fun select(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>): PostSelectBuilder<T> {
		return PostSelectBuilder(de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(expressions.toList(), false))
	}

	fun selectAll(): PostSelectBuilder<T> {
		return PostSelectBuilder(
			de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(
				listOf(de.ruegnerlukas.sqldsl.dsl.grammar.select.AllSelectExpression()),
				false
			)
		)
	}

	fun selectDistinct(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>): PostSelectBuilder<T> {
		return PostSelectBuilder(de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(expressions, true))
	}

	fun selectDistinct(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>): PostSelectBuilder<T> {
		return PostSelectBuilder(de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(expressions.toList(), false))
	}

	fun selectAllDistinct(): PostSelectBuilder<T> {
		return PostSelectBuilder(
			de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(
				listOf(de.ruegnerlukas.sqldsl.dsl.grammar.select.AllSelectExpression()),
				true
			)
		)
	}

}


class PostSelectBuilder<T: AnyValueType>(val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement) {

	fun from(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression>): PostFromBuilder<T> {
		return PostFromBuilder(select, de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement(expressions))
	}

	fun from(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression): PostFromBuilder<T> {
		return PostFromBuilder(select, de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement(expressions.toList()))
	}

}


class PostFromBuilder<T: AnyValueType>(val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement, val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement) : QueryBuilderEndStep<T> {

	fun where(condition: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereExpression): PostWhereBuilder<T> {
		return PostWhereBuilder(select, from, de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement(condition))
	}

	fun groupBy(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression>): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, null, de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement(expressions))
	}

	fun groupBy(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, null, de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement(expressions.toList()))
	}

	fun orderBy(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, null, null, null, de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, null, null, null,
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions.toList())
		)
	}

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement(
			select = select,
			from = from
		)
	}

}


class PostWhereBuilder<T: AnyValueType>(val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement, val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement, val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement) :
	QueryBuilderEndStep<T> {

	fun groupBy(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression>): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, where, de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement(expressions))
	}

	fun groupBy(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByExpression): PostGroupByBuilder<T> {
		return PostGroupByBuilder(select, from, where, de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement(expressions.toList()))
	}

	fun orderBy(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, null, null, de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions))
	}

	fun orderBy(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, null, null,
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions.toList())
		)
	}

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement(
			select = select,
			from = from,
			where = where
		)
	}
}


class PostGroupByBuilder<T: AnyValueType>(val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement, val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement, val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement?, val groupBy: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement?) :
	QueryBuilderEndStep<T> {

	fun having(condition: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingExpression): PostHavingBuilder<T> {
		return PostHavingBuilder(select, from, where, groupBy, de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingStatement(condition))
	}

	fun orderBy(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, null,
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions)
		)
	}

	fun orderBy(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, null,
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions.toList())
		)
	}

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy
		)
	}
}


class PostHavingBuilder<T: AnyValueType>(
	val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement,
	val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement,
	val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement?,
	val groupBy: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement?,
	val having: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingStatement
) : QueryBuilderEndStep<T> {

	fun orderBy(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression>): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, having,
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions)
		)
	}

	fun orderBy(vararg expressions: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression): PostOrderByBuilder<T> {
		return PostOrderByBuilder(select, from, where, groupBy, having,
			de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement(expressions.toList())
		)
	}

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having
		)
	}
}


class PostOrderByBuilder<T: AnyValueType>(
	val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement,
	val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement,
	val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement?,
	val groupBy: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement?,
	val having: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingStatement?,
	val orderBy: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement
) : QueryBuilderEndStep<T> {

	fun limit(limit: Int): PostLimitBuilder<T> {
		return PostLimitBuilder(select, from, where, groupBy, having, orderBy,
			de.ruegnerlukas.sqldsl.dsl.grammar.limit.LimitStatement(limit, null)
		)
	}

	fun limit(limit: Int, offset: Int): PostLimitBuilder<T> {
		return PostLimitBuilder(select, from, where, groupBy, having, orderBy,
			de.ruegnerlukas.sqldsl.dsl.grammar.limit.LimitStatement(limit, offset)
		)
	}

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement(
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
	val select: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement,
	val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement,
	val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement?,
	val groupBy: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement?,
	val having: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingStatement?,
	val orderBy: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement?,
	val limit: de.ruegnerlukas.sqldsl.dsl.grammar.limit.LimitStatement
) : QueryBuilderEndStep<T> {

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T> {
		return de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement(
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


