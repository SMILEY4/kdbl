package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.BooleanValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.FloatValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.IntValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.StringValueType

infix fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr.and(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndCondition(this, other)

fun and(vararg conditions: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndChainCondition(conditions.toList())
fun and(conditions: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AndChainCondition(conditions)

infix fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr.or(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrCondition(this, other)

fun or(vararg conditions: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrChainCondition(conditions.toList())
fun or(conditions: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OrChainCondition(conditions)

fun not(condition: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) = de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotCondition(condition)

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isEqual(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(this, other)
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isEqual(other: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(this, other)
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isEqual(other: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(this, other.build())
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isEqual(other: Int) = isEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isEqual(other: Float) = isEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>.isEqual(other: String) = isEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<BooleanValueType>.isEqual(other: Boolean) = isEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.BooleanLiteral(
		other
	)
)

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotEqual(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotEqualCondition(this, other)
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotEqual(other: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotEqualCondition(this, other)
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotEqual(other: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotEqualCondition(this, other.build())
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isNotEqual(other: Int) = isNotEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isNotEqual(other: Float) = isNotEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>.isNotEqual(other: String) = isNotEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<BooleanValueType>.isNotEqual(other: Boolean) = isNotEqual(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.BooleanLiteral(
		other
	)
)

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isGreaterThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isGreaterThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isGreaterThan(other: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterThanCondition(this, other.build())
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isGreaterThan(other: Int) = isGreaterThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isGreaterThan(other: Float) = isGreaterThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		other
	)
)

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isGreaterOrEqualThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterOrEqualThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isGreaterOrEqualThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterOrEqualThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isGreaterOrEqualThan(other: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.GreaterOrEqualThanCondition(this, other.build())
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isGreaterOrEqualThan(other: Int) = isGreaterOrEqualThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isGreaterOrEqualThan(other: Float) = isGreaterOrEqualThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		other
	)
)

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isLessThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isLessThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isLessThan(other: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessThanCondition(this, other.build())
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isLessThan(other: Int) = isLessThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isLessThan(other: Float) = isLessThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		other
	)
)

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isLessOrEqualThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessOrEqualThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isLessOrEqualThan(other: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessOrEqualThanCondition(this, other)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isLessOrEqualThan(other: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LessOrEqualThanCondition(this, other.build())
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isLessOrEqualThan(other: Int) = isLessOrEqualThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		other
	)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isLessOrEqualThan(other: Float) = isLessOrEqualThan(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		other
	)
)

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNull() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IsNullCondition(this)

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotNull() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IsNotNullCondition(this)

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isIn(query: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.InSubQueryCondition(this, query)
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isIn(query: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.InSubQueryCondition(this, query.build())
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isIn(values: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.InListCondition(this, values)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isIn(vararg values: Int) = isIn(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral(
		values.toList().map { de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(it) })
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isIn(vararg values: Float) = isIn(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral(
		values.toList().map { de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(it) })
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>.isIn(vararg values: String) = isIn(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral(
		values.toList().map { de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral(it) })
)

fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotIn(query: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInSubQueryCondition(this, query)
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotIn(query: QueryBuilderEndStep<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInSubQueryCondition(this, query.build())
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isNotIn(values: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.NotInListCondition(this, values)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isNotIn(vararg values: Int) = isNotIn(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral(
		values.toList().map { de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(it) })
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isNotIn(vararg values: Float) = isNotIn(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral(
		values.toList().map { de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(it) })
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>.isNotIn(vararg values: String) = isNotIn(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral(
		values.toList().map { de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral(it) })
)

fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.isBetween(lower: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, upper: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.BetweenCondition(this, lower, upper)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<IntValueType>.isBetween(lower: Int, upper: Int) = isBetween(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(
		lower
	), de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(upper)
)
fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<FloatValueType>.isBetween(lower: Float, upper: Float) = isBetween(
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(
		lower
	), de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral(upper)
)

fun de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>.isLike(pattern: String) =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.LikeCondition(this, pattern)