package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.Operation
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRef
import de.ruegnerlukas.sqldsl.core.syntax.select.AllColumnsSelectExpression
import de.ruegnerlukas.sqldsl.core.syntax.select.OperationSelectExpression
import de.ruegnerlukas.sqldsl.core.syntax.select.SelectExpression
import de.ruegnerlukas.sqldsl.core.syntax.statements.SelectStatement
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteSelectGenerator {

	fun build(select: SelectStatement): String {
		return buildToken(select).buildString()
	}

	fun buildToken(select: SelectStatement): Token {
		return ListToken()
			.add("SELECT")
			.addIf(select.distinct, "DISTINCT")
			.add(
				CsvListToken(select.expressions.map { selectExpression(it) })
			)
	}

	private fun selectExpression(e: SelectExpression): Token {
		return when (e) {
			is ColumnRef<*, *> -> SQLiteColumnRefGenerator.build(GenContext.SELECT, e)
			is TableRef -> StringToken("${SQLiteTableRefGenerator.build(GenContext.SELECT, e).buildString()}.*")
			is AllColumnsSelectExpression -> StringToken("*")
			is OperationSelectExpression -> operation(e)
			else -> {
				throw Exception("Unknown SelectExpression: $e")
			}
		}
	}

	private fun operation(e: OperationSelectExpression): Token {
		return when (e) {
			is Operation<*> -> SQLiteExpressionGenerator.buildToken(e)
			else -> {
				throw Exception("Unknown FunctionSelectExpression: $e")
			}
		}
	}

}