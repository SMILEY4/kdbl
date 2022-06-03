package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.AndCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.BetweenCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.Condition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.EqualCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.GreaterThanCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.IsNotNullCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.IsNullCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.LessThanCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.LikeCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.NotCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.OrCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.BooleanLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.CurrentTimestampLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.IntLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.ListLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.LiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.NullLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.PlaceholderLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.StringLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.AddOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.CountAllOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.CountDistinctOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.MulOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.Operation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.SubOperation
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteExpressionGenerator {

	fun build(expression: Expression<*>): String {
		return buildToken(expression).buildString()
	}

	fun buildToken(expression: Expression<*>): Token {
		return expression(expression)
	}

	private fun expression(e: Expression<*>): Token {
		return when (e) {
			is Operation -> operation(e)
			is Condition -> condition(e)
			is LiteralValue -> literalValue(e)
			else -> {
				throw Exception("Unknown Expression: $e")
			}
		}
	}

	private fun operation(e: Operation<*>): Token {
		return when (e) {
			is SubOperation -> StringToken("(${expression(e.left).buildString()}) - (${expression(e.right).buildString()})")
			is AddOperation -> StringToken("(${expression(e.left).buildString()}) + (${expression(e.right).buildString()})")
			is MulOperation -> StringToken("(${expression(e.left).buildString()}) * (${expression(e.right).buildString()})")
			is CountAllOperation -> StringToken("COUNT(*)")
			is CountDistinctOperation -> StringToken("COUNT(DISTINCT {${SQLiteColumnRefGenerator.buildString(GenContext.UNKNOWN, e.column)}})")
			else -> {
				throw Exception("Unknown Operation: $e")
			}
		}
	}

	private fun condition(e: Condition): Token {
		return when (e) {
			is NotCondition -> StringToken("NOT (${condition(e.condition).buildString()})")
			is IsNullCondition -> StringToken("(${expression(e.expression).buildString()}) IS NULL")
			is IsNotNullCondition -> StringToken("(${expression(e.expression).buildString()}) IS NOT NULL")
			is AndCondition -> StringToken("(${condition(e.left).buildString()}) AND (${condition(e.right).buildString()})")
			is OrCondition -> StringToken("(${condition(e.left).buildString()}) OR (${condition(e.right).buildString()})")
			is LikeCondition -> StringToken("(${expression(e.expression)}) LIKE '${e.pattern}'")
			is BetweenCondition -> StringToken(
				"(${expression(e.expression).buildString()}) BETWEEN (${expression(e.min).buildString()}) AND (${
					expression(
						e.max
					).buildString()
				})"
			)
			is EqualCondition<*> -> StringToken("(${expression(e.left).buildString()}) == (${expression(e.right).buildString()})")
			is LessThanCondition -> StringToken("(${expression(e.left).buildString()}) < (${expression(e.right).buildString()})")
			is GreaterThanCondition -> StringToken("(${expression(e.left).buildString()}) > (${expression(e.right).buildString()})")
			else -> {
				throw Exception("Unknown Condition: $e")
			}
		}
	}

	private fun literalValue(e: LiteralValue<*>): Token {
		return when (e) {
			is IntLiteralValue -> StringToken("${e.value}")
			is StringLiteralValue -> StringToken("'${e.value}'")
			is BooleanLiteralValue -> StringToken(if (e.value) "TRUE" else "FALSE")
			is PlaceholderLiteralValue -> StringToken("?")
			is NullLiteralValue -> StringToken("NULL")
			is CurrentTimestampLiteralValue -> StringToken("CURRENT_TIMESTAMP")
			is ListLiteralValue<*> -> StringToken("(${e.literals.map { literalValue(it) }.joinToString(",")})")
			is ColumnRef<*, *> -> SQLiteColumnRefGenerator.build(GenContext.UNKNOWN, e)
			else -> {
				throw Exception("Unknown LiteralValue: $e")
			}
		}
	}


}