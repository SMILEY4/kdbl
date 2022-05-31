package de.ruegnerlukas.sqldsl.core.builders

import de.ruegnerlukas.sqldsl.core.grammar.expression.Expression
import de.ruegnerlukas.sqldsl.core.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.core.grammar.from.JoinFromExpression
import de.ruegnerlukas.sqldsl.core.grammar.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.core.grammar.from.TableFromExpression
import de.ruegnerlukas.sqldsl.core.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.core.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.core.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl.core.grammar.refs.ColumnRef
import de.ruegnerlukas.sqldsl.core.grammar.refs.ref
import de.ruegnerlukas.sqldsl.core.grammar.select.AllColumnsOfSelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.select.AllColumnsSelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.select.ColumnSelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.HavingStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.LimitStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.OrderByEntry
import de.ruegnerlukas.sqldsl.core.grammar.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.QueryStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.SelectStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.WhereStatement
import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table

class QueryBuilder {

	fun select(block: SelectQueryBuilder.() -> Unit): PostSelectQueryBuilder {
		val selectStmt = SelectQueryBuilder(false)
			.apply(block)
			.buildStatement()
		return PostSelectQueryBuilder(selectStmt)
	}

	fun selectDistinct(block: SelectQueryBuilder.() -> Unit): PostSelectQueryBuilder {
		val selectStmt = SelectQueryBuilder(true)
			.apply(block)
			.buildStatement()
		return PostSelectQueryBuilder(selectStmt)
	}

}


class PostSelectQueryBuilder(
	private val selectStatement: SelectStatement
) {

	fun from(block: FromQueryBuilder.() -> Unit): PostFromQueryBuilder {
		val fromStmt = FromQueryBuilder().apply(block).buildStatement()
		return PostFromQueryBuilder(selectStatement, fromStmt)
	}

}


class PostFromQueryBuilder(
	private val selectStatement: SelectStatement,
	private val fromStatement: FromStatement
) {

	fun where(expression: Expression): PostWhereQueryBuilder {
		return PostWhereQueryBuilder(selectStatement, fromStatement, WhereStatement(expression))
	}

	fun groupBy(vararg columns: ColumnRef): PostGroupByQueryBuilder {
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

	fun groupBy(vararg columns: ColumnRef): PostGroupByQueryBuilder {
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


class SelectQueryBuilder(private val distinct: Boolean) {

	private val expressions = mutableListOf<SelectExpression>()

	fun column(column: Column<*, *>) {
		expressions.add(ColumnSelectExpression(ref(column), column.getColumnName()))
	}

	fun column(column: Column<*, *>, alias: String) {
		expressions.add(ColumnSelectExpression(ref(column), alias))
	}

	fun all() {
		expressions.add(AllColumnsSelectExpression())
	}

	fun all(table: Table) {
		expressions.add(AllColumnsOfSelectExpression(table))
	}

	internal fun buildStatement(): SelectStatement {
		return SelectStatement(distinct, expressions)
	}

}


class FromQueryBuilder {

	private val expressions = mutableListOf<FromExpression>()

	fun table(table: Table) {
		expressions.add(TableFromExpression(table, table.getTableName()))
	}

	fun table(table: Table, alias: String) {
		expressions.add(TableFromExpression(table, alias))
	}

	fun subQuery(query: QueryStatement, alias: String) {
		expressions.add(QueryFromExpression(query, alias))
	}

	fun joinLeft(block: JoinBuilder.() -> Unit) {
		expressions.add(JoinBuilder(JoinOp.LEFT).apply(block).build())
	}

	fun joinInner(block: JoinBuilder.() -> Unit) {
		expressions.add(JoinBuilder(JoinOp.INNER).apply(block).build())
	}

	fun joinCross(block: JoinBuilder.() -> Unit) {
		expressions.add(JoinBuilder(JoinOp.CROSS).apply(block).build())
	}

	class JoinBuilder(private val op: JoinOp) {

		private var left: FromExpression? = null
		private var right: FromExpression? = null
		private var on: Expression? = null
		private var using: List<ColumnRef>? = null

		fun left(table: Table) {
			this.left = TableFromExpression(table, table.getTableName())
		}

		fun left(table: Table, alias: String) {
			this.left = TableFromExpression(table, alias)
		}

		fun right(table: Table) {
			this.right = TableFromExpression(table, table.getTableName())
		}

		fun right(table: Table, alias: String) {
			this.right = TableFromExpression(table, alias)
		}

		fun on(expression: Expression) {
			this.on = expression
		}

		fun using(vararg columns: ColumnRef) {
			this.using = columns.toList()
		}

		internal fun build(): JoinFromExpression {
			if (on != null) {
				return JoinFromExpression(left!!, right!!, op, ConditionJoinConstraint(on!!))
			} else {
				return JoinFromExpression(left!!, right!!, op, UsingJoinConstraint(using!!))
			}
		}
	}

	fun buildStatement(): FromStatement {
		return FromStatement(expressions)
	}

}
