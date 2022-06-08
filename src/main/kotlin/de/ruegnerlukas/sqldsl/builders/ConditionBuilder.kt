package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.expr.AndChainCondition
import de.ruegnerlukas.sqldsl.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl.grammar.expr.BetweenCondition
import de.ruegnerlukas.sqldsl.grammar.expr.BooleanLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.grammar.expr.FloatLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.InListCondition
import de.ruegnerlukas.sqldsl.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl.grammar.expr.LessOrEqualThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.LessThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.LikeCondition
import de.ruegnerlukas.sqldsl.grammar.expr.ListLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.NotCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotEqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotInListCondition
import de.ruegnerlukas.sqldsl.grammar.expr.NotInSubQueryCondition
import de.ruegnerlukas.sqldsl.grammar.expr.OrChainCondition
import de.ruegnerlukas.sqldsl.grammar.expr.OrCondition
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.BooleanValueType
import de.ruegnerlukas.sqldsl.schema.FloatValueType
import de.ruegnerlukas.sqldsl.schema.IntValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType
import de.ruegnerlukas.sqldsl.schema.StringValueType

infix fun ConditionExpr.and(other: ConditionExpr) = AndCondition(this, other)

fun and(vararg conditions: ConditionExpr) = AndChainCondition(conditions.toList())
fun and(conditions: List<ConditionExpr>) = AndChainCondition(conditions)

infix fun ConditionExpr.or(other: ConditionExpr) = OrCondition(this, other)

fun or(vararg conditions: ConditionExpr) = OrChainCondition(conditions.toList())
fun or(conditions: List<ConditionExpr>) = OrChainCondition(conditions)

fun not(condition: ConditionExpr) = NotCondition(condition)

fun <T : AnyValueType> Expr<T>.isEqual(other: Expr<T>) = EqualCondition(this, other)
fun Expr<IntValueType>.isEqual(other: Int) = isEqual(IntLiteral(other))
fun Expr<FloatValueType>.isEqual(other: Float) = isEqual(FloatLiteral(other))
fun Expr<StringValueType>.isEqual(other: String) = isEqual(StringLiteral(other))
fun Expr<BooleanValueType>.isEqual(other: Boolean) = isEqual(BooleanLiteral(other))

fun <T : AnyValueType> Expr<T>.isNotEqual(other: Expr<T>) = NotEqualCondition(this, other)
fun Expr<IntValueType>.isNotEqual(other: Int) = isNotEqual(IntLiteral(other))
fun Expr<FloatValueType>.isNotEqual(other: Float) = isNotEqual(FloatLiteral(other))
fun Expr<StringValueType>.isNotEqual(other: String) = isNotEqual(StringLiteral(other))
fun Expr<BooleanValueType>.isNotEqual(other: Boolean) = isNotEqual(BooleanLiteral(other))

fun <T : NumericValueType> Expr<T>.isGreaterThan(other: Expr<T>) = GreaterThanCondition(this, other)
fun Expr<IntValueType>.isGreaterThan(other: Int) = isGreaterThan(IntLiteral(other))
fun Expr<FloatValueType>.isGreaterThan(other: Float) = isGreaterThan(FloatLiteral(other))

fun <T : NumericValueType> Expr<T>.isGreaterOrEqualThan(other: Expr<T>) = GreaterOrEqualThanCondition(this, other)
fun Expr<IntValueType>.isGreaterEqualThan(other: Int) = isGreaterOrEqualThan(IntLiteral(other))
fun Expr<FloatValueType>.isGreaterEqualThan(other: Float) = isGreaterOrEqualThan(FloatLiteral(other))

fun <T : NumericValueType> Expr<T>.isLessThan(other: Expr<T>) = LessThanCondition(this, other)
fun Expr<IntValueType>.isLessThan(other: Int) = isLessThan(IntLiteral(other))
fun Expr<FloatValueType>.isLessThan(other: Float) = isLessThan(FloatLiteral(other))

fun <T : NumericValueType> Expr<T>.isLessOrEqualThan(other: Expr<T>) = LessOrEqualThanCondition(this, other)
fun Expr<IntValueType>.isLessOrEqualThan(other: Int) = isLessOrEqualThan(IntLiteral(other))
fun Expr<FloatValueType>.isLessOrEqualThan(other: Float) = isLessOrEqualThan(FloatLiteral(other))

fun <T : AnyValueType> Expr<T>.isNull() = IsNullCondition(this)

fun <T : AnyValueType> Expr<T>.isNotNull() = IsNotNullCondition(this)

fun <T : AnyValueType> Expr<T>.isIn(query: QueryStatement) = InSubQueryCondition(this, query)
fun <T : AnyValueType> Expr<T>.isIn(query: QueryBuilderEndStep) = InSubQueryCondition(this, query.build())
fun <T : AnyValueType> Expr<T>.isIn(values: ListLiteral<T>) = InListCondition(this, values)
fun Expr<IntValueType>.isIn(vararg values: Int) = isIn(ListLiteral(values.toList().map { IntLiteral(it) }))
fun Expr<FloatValueType>.isIn(vararg values: Float) = isIn(ListLiteral(values.toList().map { FloatLiteral(it) }))
fun Expr<StringValueType>.isIn(vararg values: String) = isIn(ListLiteral(values.toList().map { StringLiteral(it) }))

fun <T : AnyValueType> Expr<T>.isNotIn(query: QueryStatement) = NotInSubQueryCondition(this, query)
fun <T : AnyValueType> Expr<T>.isNotIn(query: QueryBuilderEndStep) = NotInSubQueryCondition(this, query.build())
fun <T : AnyValueType> Expr<T>.isNotIn(values: ListLiteral<T>) = NotInListCondition(this, values)
fun Expr<IntValueType>.isNotIn(vararg values: Int) = isNotIn(ListLiteral(values.toList().map { IntLiteral(it) }))
fun Expr<FloatValueType>.isNotIn(vararg values: Float) = isNotIn(ListLiteral(values.toList().map { FloatLiteral(it) }))
fun Expr<StringValueType>.isNotIn(vararg values: String) = isNotIn(ListLiteral(values.toList().map { StringLiteral(it) }))

fun <T : NumericValueType> Expr<T>.isBetween(lower: Expr<T>, upper: Expr<T>) = BetweenCondition(this, lower, upper)
fun Expr<IntValueType>.isBetween(lower: Int, upper: Int) = isBetween(IntLiteral(lower), IntLiteral(upper))
fun Expr<FloatValueType>.isBetween(lower: Float, upper: Float) = isBetween(FloatLiteral(lower), FloatLiteral(upper))

fun Expr<StringValueType>.isLike(pattern: String) = LikeCondition(this, pattern)



// TODO between, like