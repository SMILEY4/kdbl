package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.grammar.expression.Expression
import de.ruegnerlukas.sqldsl.core.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.core.grammar.from.JoinFromExpression
import de.ruegnerlukas.sqldsl.core.grammar.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.core.grammar.from.TableFromExpression
import de.ruegnerlukas.sqldsl.core.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.core.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.core.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl.core.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.select.AllColumnsSelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.select.SelectExpression
import de.ruegnerlukas.sqldsl.core.grammar.statements.Dir
import de.ruegnerlukas.sqldsl.core.grammar.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.HavingStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.LimitStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.QueryStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.SelectStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.WhereStatement
import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class NewSqliteQueryGenerator {

	private val expressionGenerator = SqliteExpressionGenerator()

	fun build(queryStatement: QueryStatement): String {
		return ListToken()
			.add(buildSelect(queryStatement.select))
			.add(buildFrom(queryStatement.from))
			.addIf(queryStatement.where != null) { buildWhere(queryStatement.where!!) }
			.addIf(queryStatement.group != null) { buildGroupBy(queryStatement.group!!) }
			.addIf(queryStatement.having != null) { buildHaving(queryStatement.having!!) }
			.addIf(queryStatement.order != null) { buildOrderBy(queryStatement.order!!) }
			.addIf(queryStatement.limit != null) { buildLimit(queryStatement.limit!!) }
			.buildString()
	}

	private fun buildSelect(select: SelectStatement): Token {
		return ListToken()
			.add("SELECT")
			.addIf("DISTINCT") { select.distinct }
			.add(
				CsvListToken(
					select.expressions.map { selectExpression(it) }
				)
			)
	}

	private fun selectExpression(expression: SelectExpression): Token {
		return when (expression) {
			is AllColumnsSelectExpression -> StringToken("*")
			is Table -> StringToken("${expression.getTableName()}.*")
			is Column<*, *> -> StringToken(expression.getColumnName())
			is AliasSelectExpression -> StringToken("${selectExpression(expression.source)} AS ${expression.alias}")
			else -> {
				throw IllegalStateException("Unknown SelectExpression: $expression")
			}
		}
	}


	private fun buildFrom(from: FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(
				CsvListToken(
					from.expressions.map { fromExpression(it) }
				)
			)
	}

	private fun fromExpression(expression: FromExpression): Token {
		return when (expression) {
			is TableFromExpression -> StringToken("${expression.table.getTableName()} AS ${expression.alias}")
			is QueryFromExpression -> StringToken("(${build(expression.query)}) AS ${expression.alias}")
			is JoinFromExpression -> join(expression)
			else -> {
				throw IllegalStateException("Unknown FromExpression: $expression")
			}
		}
	}

	private fun join(expression: JoinFromExpression): Token {
		return ListToken()
			.add(fromExpression(expression.left))
			.add(mapJoinOp(expression.joinOp))
			.add(fromExpression(expression.right))
			.then {
				when (expression.constraint) {
					is ConditionJoinConstraint -> add(joinConstraintCondition(expression.constraint))
					is UsingJoinConstraint -> add(joinConstraintUsing(expression.constraint))
				}
			}
	}

	private fun mapJoinOp(op: JoinOp): String {
		return when (op) {
			JoinOp.LEFT -> "JOIN"
			JoinOp.INNER -> "JOIN INNER"
			JoinOp.CROSS -> "JOIN CROSS"
		}
	}

	private fun joinConstraintCondition(constraint: ConditionJoinConstraint): Token {
		return ListToken()
			.add("ON")
			.add(expression(constraint.expression))
	}

	private fun joinConstraintUsing(constraint: UsingJoinConstraint): Token {
		return ListToken()
			.add("USING")
			.add(
				CsvListToken(
					constraint.columns.map { StringToken("${it.getTableName()}.${it.getColumnName()}") }
				)
			)
	}

	private fun buildWhere(where: WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(expression(where.expression))
	}

	private fun buildGroupBy(groupBy: GroupByStatement): Token {
		return ListToken()
			.add("GROUP BY")
			.add(
				CsvListToken(
					groupBy.columns.map { StringToken("${it.getTableName()}.${it.getColumnName()}") }
				)
			)
	}

	private fun buildHaving(having: HavingStatement): Token {
		return ListToken()
			.add("HAVING")
			.add(expression(having.expression))
	}

	private fun buildOrderBy(orderBy: OrderByStatement): Token {
		return ListToken()
			.add("ORDER BY")
			.add(
				CsvListToken(
					orderBy.entries.map { StringToken("${it.column.getTableName()}.${it.column.getColumnName()} ${mapDir(it.dir)}") }
				)
			)
	}

	private fun mapDir(dir: Dir): String {
		return when (dir) {
			Dir.ASC -> "ASC"
			Dir.DESC -> "DESC"
		}
	}

	private fun buildLimit(limit: LimitStatement): Token {
		return ListToken()
			.add("LIMIT")
			.add(limit.limit.toString())
	}

	private fun expression(expression: Expression): Token {
		return expressionGenerator.buildToken(expression)
	}

}