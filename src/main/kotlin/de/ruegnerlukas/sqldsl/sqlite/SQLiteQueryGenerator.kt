package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.actions.query.JoinClauseQuerySource
import de.ruegnerlukas.sqldsl.core.actions.query.JoinType
import de.ruegnerlukas.sqldsl.core.actions.query.QueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.QuerySource
import de.ruegnerlukas.sqldsl.core.actions.query.QueryStatement
import de.ruegnerlukas.sqldsl.core.actions.query.TableRef
import de.ruegnerlukas.sqldsl.core.generators.QueryGenerator
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SQLiteQueryGenerator : QueryGenerator {

	private val expressionGenerator = SQLiteQueryExpressionGenerator()
	private val utils = SQLiteUtilityGenerator()

	override fun build(stmt: QueryStatement): String {
		return ListToken()
			.add(fromStatement(stmt))
			.buildString()
	}


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
			else -> NoOpToken()
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


}