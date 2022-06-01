package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.from.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.core.syntax.from.FromExpression
import de.ruegnerlukas.sqldsl.core.syntax.from.JoinConstraint
import de.ruegnerlukas.sqldsl.core.syntax.from.JoinFromExpression
import de.ruegnerlukas.sqldsl.core.syntax.from.JoinOp
import de.ruegnerlukas.sqldsl.core.syntax.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.core.syntax.from.UsingJoinConstraint
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRef
import de.ruegnerlukas.sqldsl.core.syntax.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteFromGenerator {

	fun build(from: FromStatement): String {
		return buildToken(from).buildString()
	}

	fun buildToken(from: FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(
				CsvListToken(from.expressions.map { fromExpression(it) })
			)
	}

	private fun fromExpression(e: FromExpression): Token {
		return when (e) {
			is TableRef -> SQLiteRefGenerator.table(e)
			is JoinFromExpression -> join(e)
			is QueryFromExpression -> subQuery(e)
			else -> {
				throw Exception("Unknown FromExpression: $e")
			}
		}
	}

	private fun join(e: JoinFromExpression): Token {
		return ListToken()
			.add(fromExpression(e.left))
			.add(mapJoinOp(e.joinOp))
			.add(fromExpression(e.right))
			.add(joinConstraint(e.constraint))
	}

	private fun mapJoinOp(op: JoinOp): String {
		return when (op) {
			JoinOp.LEFT -> "JOIN"
			JoinOp.INNER -> "INNER JOIN"
			JoinOp.CROSS -> "CROSS JOIN"
		}
	}

	private fun joinConstraint(e: JoinConstraint): Token {
		return when (e) {
			is ConditionJoinConstraint -> {
				ListToken()
					.add("ON")
					.add(SQLiteExpressionGenerator.buildToken(e.expression))
			}
			is UsingJoinConstraint -> {
				ListToken()
					.add("USING")
					.then {
						e.columns.forEach { add(SQLiteRefGenerator.column(it)) }
					}
			}
			else -> {
				throw Exception("Unknown JoinConstraint: $e")
			}
		}
	}

	private fun subQuery(e: QueryFromExpression): Token {
		return SQLiteQueryGenerator.buildToken(e.query)
	}


}