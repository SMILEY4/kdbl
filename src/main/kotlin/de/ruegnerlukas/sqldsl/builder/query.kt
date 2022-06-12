package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.FromStatement
import de.ruegnerlukas.sqldsl.dsl.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.HavingStatement
import de.ruegnerlukas.sqldsl.dsl.statements.LimitStatement
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByElement
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectAllElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectStatement
import de.ruegnerlukas.sqldsl.dsl.statements.WhereStatement

object QueryBuilder {

	class StartQueryBuilder {

		fun select(e: List<SelectElement>) = PostSelectBuilder(
			SelectStatement(false, e)
		)

		fun selectDistinct(e: List<SelectElement>) = PostSelectBuilder(
			SelectStatement(true, e)
		)

		fun selectAll() = PostSelectBuilder(
			SelectStatement(false, listOf(SelectAllElement()))
		)

		fun selectDistinctAll() = PostSelectBuilder(
			SelectStatement(true, listOf(SelectAllElement()))
		)

	}


	class PostSelectBuilder(
		private val select: SelectStatement
	) {

		fun from(e: List<FromElement>) = PostFromBuilder(
			select,
			FromStatement(e)
		)

		fun from(vararg e: FromElement) = from(e.toList())

	}


	class PostFromBuilder(
		private val select: SelectStatement,
		private val from: FromStatement
	) : QueryBuilderEndStep<Any> {

		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from
		)

		fun where(condition: Expr<Boolean>) = PostWhereBuilder(
			select,
			from,
			WhereStatement(condition)
		)

		fun groupBy(e: List<Expr<*>>) = PostGroupByBuilder(
			select,
			from,
			null,
			GroupByStatement(e)
		)

		fun groupBy(vararg e: Expr<*>) = groupBy(e.toList())

		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			null,
			null,
			null,
			OrderByStatement(e)
		)

		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	class PostWhereBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement
	) : QueryBuilderEndStep<Any> {

		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where
		)

		fun groupBy(e: List<Expr<*>>) = PostGroupByBuilder(
			select,
			from,
			where,
			GroupByStatement(e)
		)

		fun groupBy(vararg e: Expr<*>) = groupBy(e.toList())

		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			where,
			null,
			null,
			OrderByStatement(e)
		)

		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	class PostGroupByBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement
	) : QueryBuilderEndStep<Any> {

		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy
		)

		fun having(condition: Expr<Boolean>) = PostHavingBuilder(
			select,
			from,
			where,
			groupBy,
			HavingStatement(condition)
		)

		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			where,
			groupBy,
			null,
			OrderByStatement(e)
		)

		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	class PostHavingBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement,
		private val having: HavingStatement
	) : QueryBuilderEndStep<Any> {

		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having
		)

		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			where,
			groupBy,
			having,
			OrderByStatement(e)
		)

		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	class PostOrderByBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement?,
		private val having: HavingStatement?,
		private val orderBy: OrderByStatement
	) : QueryBuilderEndStep<Any> {

		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having,
			orderBy = orderBy
		)

		fun limit(limit: Int, offset: Int) = PostLimitBuilder(
			select,
			from,
			where,
			groupBy,
			having,
			orderBy,
			LimitStatement(limit, offset)
		)

		fun limit(limit: Int) = limit(limit, 0)

	}


	class PostLimitBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement?,
		private val having: HavingStatement?,
		private val orderBy: OrderByStatement,
		private val limit: LimitStatement
	) : QueryBuilderEndStep<Any> {

		override fun <T> build() = QueryStatement<T>(
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