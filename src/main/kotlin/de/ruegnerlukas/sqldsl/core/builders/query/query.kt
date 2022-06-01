package de.ruegnerlukas.sqldsl.core.builders.query

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.HavingStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.LimitStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.OrderByEntry
import de.ruegnerlukas.sqldsl.core.syntax.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.QueryStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.SelectStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.WhereStatement


class PostFromQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement
) {

	fun where(expression: Expression): PostWhereQueryBuilder {
		return PostWhereQueryBuilder(selectStatement, fromStatement, WhereStatement(expression))
	}

	fun groupBy(vararg columns: ColumnRef<*, *>): PostGroupByQueryBuilder {
		return PostGroupByQueryBuilder(selectStatement, fromStatement, null, GroupByStatement(columns.toList()))
	}

	fun orderBy(vararg entries: OrderByEntry): PostOrderByQueryBuilder {
		return PostOrderByQueryBuilder(selectStatement, fromStatement, null, null, null, OrderByStatement(entries.toList()))
	}

	fun limit(limit: Int): PostLimitQueryBuilder {
		return PostLimitQueryBuilder(selectStatement, fromStatement, null, null, null, null, LimitStatement(limit))
	}

	fun build(): QueryStatement {
		return QueryStatement(selectStatement, fromStatement, null, null, null, null, null)
	}

}


class PostWhereQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement,
	private val whereStatement: WhereStatement
) {

	fun groupBy(vararg columns: ColumnRef<*, *>): PostGroupByQueryBuilder {
		return PostGroupByQueryBuilder(selectStatement, fromStatement, whereStatement, GroupByStatement(columns.toList()))
	}

	fun orderBy(vararg entries: OrderByEntry): PostOrderByQueryBuilder {
		return PostOrderByQueryBuilder(selectStatement, fromStatement, whereStatement, null, null, OrderByStatement(entries.toList()))
	}

	fun limit(limit: Int): PostLimitQueryBuilder {
		return PostLimitQueryBuilder(selectStatement, fromStatement, whereStatement, null, null, null, LimitStatement(limit))
	}

	fun build(): QueryStatement {
		return QueryStatement(selectStatement, fromStatement, whereStatement, null, null, null, null)
	}

}


class PostGroupByQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement,
	private val whereStatement: WhereStatement?,
	private val groupByStatement: GroupByStatement
) {

	fun having(expression: Expression): PostHavingQueryBuilder {
		return PostHavingQueryBuilder(selectStatement, fromStatement, whereStatement, groupByStatement, HavingStatement(expression))
	}

	fun orderBy(vararg entries: OrderByEntry): PostOrderByQueryBuilder {
		return PostOrderByQueryBuilder(
			selectStatement,
			fromStatement,
			whereStatement,
			groupByStatement,
			null,
			OrderByStatement(entries.toList())
		)
	}

	fun limit(limit: Int): PostLimitQueryBuilder {
		return PostLimitQueryBuilder(selectStatement, fromStatement, whereStatement, groupByStatement, null, null, LimitStatement(limit))
	}

	fun build(): QueryStatement {
		return QueryStatement(selectStatement, fromStatement, whereStatement, groupByStatement, null, null, null)
	}

}


class PostHavingQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement,
	private val whereStatement: WhereStatement?,
	private val groupByStatement: GroupByStatement?,
	private val havingStatement: HavingStatement
) {

	fun orderBy(vararg entries: OrderByEntry): PostOrderByQueryBuilder {
		return PostOrderByQueryBuilder(
			selectStatement,
			fromStatement,
			whereStatement,
			groupByStatement,
			havingStatement,
			OrderByStatement(entries.toList())
		)
	}

	fun limit(limit: Int): PostLimitQueryBuilder {
		return PostLimitQueryBuilder(
			selectStatement,
			fromStatement,
			whereStatement,
			groupByStatement,
			havingStatement,
			null,
			LimitStatement(limit)
		)
	}

	fun build(): QueryStatement {
		return QueryStatement(selectStatement, fromStatement, whereStatement, groupByStatement, havingStatement, null, null)
	}

}


class PostOrderByQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement,
	private val whereStatement: WhereStatement?,
	private val groupByStatement: GroupByStatement?,
	private val havingStatement: HavingStatement?,
	private val orderByStatement: OrderByStatement
) {

	fun limit(limit: Int): PostLimitQueryBuilder {
		return PostLimitQueryBuilder(
			selectStatement,
			fromStatement,
			whereStatement,
			groupByStatement,
			havingStatement,
			orderByStatement,
			LimitStatement(limit)
		)
	}

	fun build(): QueryStatement {
		return QueryStatement(selectStatement, fromStatement, whereStatement, groupByStatement, havingStatement, orderByStatement, null)
	}

}


class PostLimitQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement,
	private val whereStatement: WhereStatement?,
	private val groupByStatement: GroupByStatement?,
	private val havingStatement: HavingStatement?,
	private val orderByStatement: OrderByStatement?,
	private val limitStatement: LimitStatement
) {

	fun build(): QueryStatement {
		return QueryStatement(
			selectStatement,
			fromStatement,
			whereStatement,
			groupByStatement,
			havingStatement,
			orderByStatement,
			limitStatement
		)
	}

}
