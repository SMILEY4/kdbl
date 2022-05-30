package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.actions.query.AliasSelectColumn
import de.ruegnerlukas.sqldsl.core.actions.query.AllOfTableSelectColumn
import de.ruegnerlukas.sqldsl.core.actions.query.AllSelectColumn
import de.ruegnerlukas.sqldsl.core.actions.query.ColumnRef
import de.ruegnerlukas.sqldsl.core.actions.query.CountAllSelectColumn
import de.ruegnerlukas.sqldsl.core.actions.query.CountSelectColumn
import de.ruegnerlukas.sqldsl.core.actions.query.Dir
import de.ruegnerlukas.sqldsl.core.actions.query.JoinClauseQuerySource
import de.ruegnerlukas.sqldsl.core.actions.query.JoinType
import de.ruegnerlukas.sqldsl.core.actions.query.QueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.QuerySource
import de.ruegnerlukas.sqldsl.core.actions.query.QueryStatement
import de.ruegnerlukas.sqldsl.core.actions.query.SelectColumn
import de.ruegnerlukas.sqldsl.core.actions.query.TableRef
import de.ruegnerlukas.sqldsl.core.generators.QueryGenerator
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SQLiteQueryGenerator : QueryGenerator {

	private val expressionGenerator = SQLiteQueryExpressionGenerator()
	private val utils = SQLiteUtilityGenerator()

	override fun build(stmt: QueryStatement): String {
		return ListToken()
			.add(selectStatement(stmt))
			.add(fromStatement(stmt))
			.add(orderByStatement(stmt))
			.buildString()
	}


	/**
	 * SELECT
	 */
	private fun selectStatement(stmt: QueryStatement): Token {
		return ListToken()
			.add("SELECT")
			.add(
				CsvListToken(
					stmt.selectColumns.map { selectColumn(it) }
				)
			)
	}

	private fun selectColumn(entry: SelectColumn): Token {
		return when (entry) {
			is ColumnRef<*, *> -> utils.columnRef(entry)
			is AliasSelectColumn -> ListToken().add(selectColumn(entry.selectColumn)).add("AS").add(entry.alias)
			is AllSelectColumn -> StringToken("*")
			is AllOfTableSelectColumn -> StringToken("${entry.table.getTableName()}.*")
			is CountAllSelectColumn -> StringToken("COUNT(*)")
			is CountSelectColumn -> StringToken("COUNT(${utils.columnRef(entry.column).buildString()})")
			else -> {
				throw IllegalStateException("Illegal select-column: $entry")
			}
		}
	}


	/**
	 * FROM
	 */
	private fun fromStatement(stmt: QueryStatement): Token {
		return ListToken()
			.add("FROM")
			.add(
				CsvListToken(
					stmt.querySources.map { querySource(it) }
				)
			)
	}

	private fun querySource(source: QuerySource): Token {
		return when (source) {
			is TableRef -> utils.tableRef(source)
			is JoinClauseQuerySource -> querySourceJoin(source)
			else -> {
				throw IllegalStateException("Illegal query-source: $source")
			}
		}
	}


	private fun querySourceJoin(source: JoinClauseQuerySource): Token {
		return ListToken()
			.add(querySource(source.source))
			.add(mapJoinType(source.type))
			.add(querySource(source.other))
			.add("ON")
			.add(
				GroupToken(
					queryExpression(source.onExpression)
				)
			)
	}

	private fun mapJoinType(type: JoinType): String {
		return when (type) {
			JoinType.INNER -> "INNER JOIN"
			JoinType.LEFT -> "LEFT JOIN"
			JoinType.CROSS -> "CROSS JOIN"
		}
	}

	private fun queryExpression(exp: QueryExp): Token {
		return expressionGenerator.buildToken(exp)
	}


	/**
	 * ORDER BY
	 */
	private fun orderByStatement(stmt: QueryStatement): Token {
		if (stmt.orderBy.isEmpty()) {
			return NoOpToken()
		} else {
			return ListToken()
				.add("ORDER BY")
				.add(
					CsvListToken(
						stmt.orderBy.map {
							ListToken()
								.add(utils.columnRef(it.first))
								.add(mapDir(it.second))
						}
					)
				)
		}
	}

	private fun mapDir(dir: Dir): String {
		return when (dir) {
			Dir.ASC -> "ASC"
			Dir.DESC -> "DESC"
		}
	}


}