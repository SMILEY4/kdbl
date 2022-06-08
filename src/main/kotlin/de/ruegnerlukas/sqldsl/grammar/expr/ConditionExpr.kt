package de.ruegnerlukas.sqldsl.grammar.expr

import de.ruegnerlukas.sqldsl.grammar.having.ConditionHavingExpression
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.where.ConditionWhereExpression
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.BooleanValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType
import de.ruegnerlukas.sqldsl.schema.StringValueType

interface ConditionExpr : OperationExpr<BooleanValueType>, ConditionWhereExpression, ConditionHavingExpression

class AndCondition(val left: ConditionExpr, val right: ConditionExpr) : ConditionExpr

class AndChainCondition(val expressions: List<ConditionExpr>) : ConditionExpr

class OrCondition(val left: ConditionExpr, val right: ConditionExpr) : ConditionExpr

class OrChainCondition(val expressions: List<ConditionExpr>) : ConditionExpr

class NotCondition(val expression: ConditionExpr) : ConditionExpr

class EqualCondition<T : AnyValueType>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class NotEqualCondition<T : AnyValueType>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class GreaterThanCondition<T : NumericValueType>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class GreaterOrEqualThanCondition<T : NumericValueType>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class LessThanCondition<T : NumericValueType>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class LessOrEqualThanCondition<T : NumericValueType>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class IsNotNullCondition<T : AnyValueType>(val expression: Expr<T>) : ConditionExpr

class IsNullCondition<T : AnyValueType>(val expression: Expr<T>) : ConditionExpr

class InListCondition<T : AnyValueType>(val expression: Expr<T>, val list: ListLiteral<T>) : ConditionExpr

class NotInListCondition<T : AnyValueType>(val expression: Expr<T>, val list: ListLiteral<T>) : ConditionExpr

class InSubQueryCondition<T : AnyValueType>(val expression: Expr<T>, val query: QueryStatement) : ConditionExpr

class NotInSubQueryCondition<T : AnyValueType>(val expression: Expr<T>, val query: QueryStatement) : ConditionExpr

class BetweenCondition<T : NumericValueType>(val expression: Expr<T>, val lower: Expr<T>, val upper: Expr<T>) : ConditionExpr

class LikeCondition(val expression: Expr<StringValueType>, val pattern: String) : ConditionExpr

