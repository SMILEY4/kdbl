package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.statements.FromElement
import de.ruegnerlukas.kdbl.dsl.statements.FromStatement
import de.ruegnerlukas.kdbl.dsl.statements.GroupByStatement
import de.ruegnerlukas.kdbl.dsl.statements.HavingStatement
import de.ruegnerlukas.kdbl.dsl.statements.LimitStatement
import de.ruegnerlukas.kdbl.dsl.statements.OrderByElement
import de.ruegnerlukas.kdbl.dsl.statements.OrderByStatement
import de.ruegnerlukas.kdbl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.QueryStatement
import de.ruegnerlukas.kdbl.dsl.statements.SelectAllElement
import de.ruegnerlukas.kdbl.dsl.statements.SelectElement
import de.ruegnerlukas.kdbl.dsl.statements.SelectStatement
import de.ruegnerlukas.kdbl.dsl.statements.WhereStatement

/**
 * Builder(-chain) for a [QueryStatement]
 */
object QueryBuilder {

	/**
	 * Builder-step with start-options
	 */
	class StartQueryBuilder {

		/**
		 * Select the given columns
		 */
		fun select(e: List<SelectElement>) = PostSelectBuilder(
			SelectStatement(false, e)
		)


		/**
		 * Select the given (distinct) columns
		 */
		fun selectDistinct(e: List<SelectElement>) = PostSelectBuilder(
			SelectStatement(true, e)
		)


		/**
		 * Select all columns
		 */
		fun selectAll() = PostSelectBuilder(
			SelectStatement(false, listOf(SelectAllElement()))
		)


		/**
		 * Select all distinct columns
		 */
		fun selectDistinctAll() = PostSelectBuilder(
			SelectStatement(true, listOf(SelectAllElement()))
		)

	}


	/**
	 * Builder-step with options for after the "select"-statement
	 */
	class PostSelectBuilder(
		private val select: SelectStatement
	) {

		/**
		 * specify the tables/sources to query from
		 */
		fun from(e: List<FromElement>) = PostFromBuilder(
			select,
			FromStatement(e)
		)


		/**
		 * specify the tables/sources to query from
		 */
		fun from(vararg e: FromElement) = from(e.toList())

	}


	/**
	 * Builder-step with options for after the "from"-statement
	 */
	class PostFromBuilder(
		private val select: SelectStatement,
		private val from: FromStatement
	) : QueryBuilderEndStep<Any> {

		/**
		 * Build the [QueryStatement] with the current data
		 */
		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from
		)


		/**
		 * Only include the rows that match the given condition
		 */
		fun where(condition: Expr<Boolean>) = PostWhereBuilder(
			select,
			from,
			WhereStatement(condition)
		)


		/**
		 * Group all rows by the given columns/expressions
		 */
		fun groupBy(e: List<Expr<*>>) = PostGroupByBuilder(
			select,
			from,
			null,
			GroupByStatement(e)
		)


		/**
		 * Group all rows by the given columns/expressions
		 */
		fun groupBy(vararg e: Expr<*>) = groupBy(e.toList())


		/**
		 * Order all rows by the given columns/expressions
		 */
		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			null,
			null,
			null,
			OrderByStatement(e)
		)


		/**
		 * Order all rows by the given columns/expressions
		 */
		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	/**
	 * Builder-step with options for after the "where"-statement
	 */
	class PostWhereBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement
	) : QueryBuilderEndStep<Any> {

		/**
		 * Build the [QueryStatement] with the current data
		 */
		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where
		)


		/**
		 * Group the selected rows by the given columns/expressions
		 */
		fun groupBy(e: List<Expr<*>>) = PostGroupByBuilder(
			select,
			from,
			where,
			GroupByStatement(e)
		)


		/**
		 * Group the selected rows by the given columns/expressions
		 */
		fun groupBy(vararg e: Expr<*>) = groupBy(e.toList())


		/**
		 * Order the selected rows by the given columns/expressions
		 */
		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			where,
			null,
			null,
			OrderByStatement(e)
		)


		/**
		 * Order the selected rows by the given columns/expressions
		 */
		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	/**
	 * Builder-step with options for after the "group-by"-statement
	 */
	class PostGroupByBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement
	) : QueryBuilderEndStep<Any> {

		/**
		 * Build the [QueryStatement] with the current data
		 */
		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy
		)


		/**
		 * Only return the rows/groups that match the given condition
		 */
		fun having(condition: Expr<Boolean>) = PostHavingBuilder(
			select,
			from,
			where,
			groupBy,
			HavingStatement(condition)
		)


		/**
		 * Order the rows/groups by the given columns/expressions
		 */
		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			where,
			groupBy,
			null,
			OrderByStatement(e)
		)


		/**
		 * Order the rows/groups by the given columns/expressions
		 */
		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	/**
	 * Builder-step with options for after the "having"-statement
	 */
	class PostHavingBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement,
		private val having: HavingStatement
	) : QueryBuilderEndStep<Any> {

		/**
		 * Build the [QueryStatement] with the current data
		 */
		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having
		)


		/**
		 * Order the rows/groups by the given columns/expressions
		 */
		fun orderBy(e: List<OrderByElement>) = PostOrderByBuilder(
			select,
			from,
			where,
			groupBy,
			having,
			OrderByStatement(e)
		)


		/**
		 * Order the rows/groups by the given columns/expressions
		 */
		fun orderBy(vararg e: OrderByElement) = orderBy(e.toList())

	}


	/**
	 * Builder-step with options for after the "order-by"-statement
	 */
	class PostOrderByBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement?,
		private val having: HavingStatement?,
		private val orderBy: OrderByStatement
	) : QueryBuilderEndStep<Any> {

		/**
		 * Build the [QueryStatement] with the current data
		 */
		override fun <T> build() = QueryStatement<T>(
			select = select,
			from = from,
			where = where,
			groupBy = groupBy,
			having = having,
			orderBy = orderBy
		)


		/**
		 * Limit and/or offset the resulting rows
		 */
		fun limit(limit: Int, offset: Int) = PostLimitBuilder(
			select,
			from,
			where,
			groupBy,
			having,
			orderBy,
			LimitStatement(limit, offset)
		)


		/**
		 * Limit and/or offset the resulting rows
		 */
		fun limit(limit: Int) = limit(limit, 0)


		/**
		 * Limit and/or offset the resulting rows according to the given page and size
		 */
		fun paged(pageIndex: Int, pageSize: Int) = limit(pageSize, pageIndex * pageSize)

	}


	/**
	 * Builder-step with options for after the "limit"-statement
	 */
	class PostLimitBuilder(
		private val select: SelectStatement,
		private val from: FromStatement,
		private val where: WhereStatement?,
		private val groupBy: GroupByStatement?,
		private val having: HavingStatement?,
		private val orderBy: OrderByStatement,
		private val limit: LimitStatement
	) : QueryBuilderEndStep<Any> {

		/**
		 * Build the [QueryStatement] with the current data
		 */
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