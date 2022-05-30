package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.actions.query.AttributeExp
import de.ruegnerlukas.sqldsl.core.actions.query.BetweenLogicalQueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.BinaryLogicalQueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.ColumnAttributeExp
import de.ruegnerlukas.sqldsl.core.actions.query.ColumnRef
import de.ruegnerlukas.sqldsl.core.actions.query.ComparisonOp
import de.ruegnerlukas.sqldsl.core.actions.query.ComparisonQueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.ConstAttributeExp
import de.ruegnerlukas.sqldsl.core.actions.query.LogicalOp
import de.ruegnerlukas.sqldsl.core.actions.query.LogicalQueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.QueryExp
import de.ruegnerlukas.sqldsl.core.actions.query.UnaryLogicalQueryExp
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SQLiteQueryExpressionGenerator {

	private val utils = SQLiteUtilityGenerator()

	fun build(expression: QueryExp): String = buildToken(expression).buildString()

	fun buildToken(expression: QueryExp): Token {
		return when (expression) {
			is ComparisonQueryExp -> comparison(expression)
			is LogicalQueryExp -> logical(expression)
			is AttributeExp -> attribute(expression)
			else -> {
				throw IllegalStateException("Illegal expression: $expression")
			}
		}
	}

	private fun comparison(expression: ComparisonQueryExp): Token {
		return ListToken()
			.add(buildToken(expression.first))
			.add(mapComparisonOp(expression.op))
			.add(buildToken(expression.second))
	}

	private fun mapComparisonOp(op: ComparisonOp): String {
		return when (op) {
			ComparisonOp.EQ -> "="
			ComparisonOp.NEQ -> "!="
			ComparisonOp.GT -> ">"
			ComparisonOp.GTE -> ">="
			ComparisonOp.LT -> "<"
			ComparisonOp.LTE -> "<="
		}
	}

	private fun logical(expression: LogicalQueryExp): Token {
		return when (expression) {
			is UnaryLogicalQueryExp -> logicalUnary(expression)
			is BinaryLogicalQueryExp -> logicalBinary(expression)
			is BetweenLogicalQueryExp -> logicalBetween(expression)
			else -> {
				throw IllegalStateException("Illegal logical-expression: $expression")
			}
		}
	}

	private fun logicalUnary(expression: UnaryLogicalQueryExp): Token {
		return ListToken()
			.add(mapLogicalOp(expression.op))
			.add(GroupToken(buildToken(expression.expr)))
	}

	private fun logicalBinary(expression: BinaryLogicalQueryExp): Token {
		return ListToken()
			.add(buildToken(expression.first))
			.add(mapLogicalOp(expression.op))
			.add(buildToken(expression.second))
	}

	private fun logicalBetween(expression: BetweenLogicalQueryExp): Token {
		return ListToken()
			.add(buildToken(expression.value))
			.add(mapLogicalOp(expression.op))
			.add(buildToken(expression.min))
			.add("AND")
			.add(buildToken(expression.max))
	}


	private fun mapLogicalOp(op: LogicalOp): String {
		return when (op) {
			LogicalOp.EXISTS -> "EXISTS"
			LogicalOp.NOT -> "NOT"
			LogicalOp.IS_NULL -> "IS NULL"
			LogicalOp.AND -> "AND"
			LogicalOp.OR -> "OR"
			LogicalOp.IN -> "IN"
			LogicalOp.NOT_IN -> "NOT IN"
			LogicalOp.LIKE -> "LIKE"
			LogicalOp.CONCAT -> "||"
			LogicalOp.BETWEEN -> "BETWEEN"
		}
	}

	private fun attribute(expression: AttributeExp): Token {
		return when (expression) {
			is ColumnAttributeExp -> columnAttribute(expression)
			is ConstAttributeExp -> constAttribute(expression)
			else -> {
				throw IllegalStateException("Illegal attribute-expression: $expression")
			}
		}
	}

	private fun columnAttribute(expression: ColumnAttributeExp): Token {
		return when (expression) {
			is ColumnRef<*, *> -> utils.columnRef(expression)
			else -> {
				throw IllegalStateException("Illegal column-attribute-expression: $expression")
			}
		}
	}

	private fun constAttribute(expression: ConstAttributeExp): Token {
		return StringToken(expression.valueAsString())
	}

}