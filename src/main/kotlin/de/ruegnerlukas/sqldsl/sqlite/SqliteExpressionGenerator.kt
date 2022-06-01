package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.grammar.expression.AddOperation
import de.ruegnerlukas.sqldsl.core.grammar.expression.AndCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.BetweenCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.BooleanLiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.Condition
import de.ruegnerlukas.sqldsl.core.grammar.expression.CurrentTimestampLiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.EqualCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.Expression
import de.ruegnerlukas.sqldsl.core.grammar.expression.IntLiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.IsNotNullCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.IsNullCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.LessThanCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.LikeCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.ListLiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.LiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.MulOperation
import de.ruegnerlukas.sqldsl.core.grammar.expression.NotCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.NullLiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.Operation
import de.ruegnerlukas.sqldsl.core.grammar.expression.OrCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.StringLiteralValue
import de.ruegnerlukas.sqldsl.core.grammar.expression.SubOperation
import de.ruegnerlukas.sqldsl.core.grammar.refs.LiteralColumnRef
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SqliteExpressionGenerator {

	fun build(e: Expression): String {
		return buildToken(e).buildString()
	}

	fun buildToken(e: Expression): Token {
		return when (e) {
			is LiteralValue -> literal(e)
			is Operation -> operation(e)
			is Condition -> condition(e)
			else -> {
				throw IllegalStateException("Unknown Expression: $e")
			}
		}
	}

	private fun literal(e: LiteralValue): Token {
		return when (e) {
			is IntLiteralValue -> StringToken(e.value.toString())
			is StringLiteralValue -> StringToken(e.value)
			is BooleanLiteralValue -> StringToken(if (e.value) "True" else "False")
			is NullLiteralValue -> StringToken("NULL")
			is CurrentTimestampLiteralValue -> StringToken("CURRENT_TIMESTAMP")
			is ListLiteralValue -> GroupToken(CsvListToken(e.literals.map { literal(it) }))
			is LiteralColumnRef -> StringToken("${e.getTableName()}.${e.getColumnName()}")
			else -> {
				throw IllegalStateException("Unknown LiteralValue: $e")
			}
		}
	}

	private fun operation(e: Operation): Token {
		return when (e) {
			is SubOperation -> ListToken().add(buildToken(e.left)).add("-").add(buildToken(e.right))
			is AddOperation -> ListToken().add(buildToken(e.left)).add("+").add(buildToken(e.right))
			is MulOperation -> ListToken().add(buildToken(e.left)).add("*").add(buildToken(e.right))
			else -> {
				throw IllegalStateException("Unknown Operation: $e")
			}
		}
	}

	private fun condition(e: Condition): Token {
		return when (e) {
			is NotCondition -> ListToken().add("NOT").add(buildToken(e.expression))
			is IsNullCondition -> ListToken().add(buildToken(e.expression)).add("IS NULL")
			is IsNotNullCondition -> ListToken().add(buildToken(e.expression)).add("IS NOT NULL")
			is AndCondition -> ListToken().add(buildToken(e.left)).add("AND").add(buildToken(e.right))
			is OrCondition -> ListToken().add(buildToken(e.left)).add("OR").add(buildToken(e.right))
			is LikeCondition -> ListToken().add(buildToken(e.expression)).add("LIKE").add(e.pattern)
			is BetweenCondition -> ListToken().add(buildToken(e.expression)).add("BETWEEN").add(buildToken(e.min)).add("AND")
				.add(buildToken(e.max))
			is EqualCondition -> ListToken().add(buildToken(e.left)).add("==").add(buildToken(e.right))
			is LessThanCondition -> ListToken().add(buildToken(e.left)).add("<=").add(buildToken(e.right))
			else -> {
				throw IllegalStateException("Unknown Condition: $e")
			}
		}
	}


}