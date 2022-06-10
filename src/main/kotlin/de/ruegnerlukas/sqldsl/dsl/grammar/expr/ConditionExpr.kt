package de.ruegnerlukas.sqldsl.dsl.grammar.expr

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.BooleanValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.StringValueType

interface ConditionExpr : de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<BooleanValueType>,
	de.ruegnerlukas.sqldsl.dsl.grammar.where.ConditionWhereExpression, de.ruegnerlukas.sqldsl.dsl.grammar.having.ConditionHavingExpression

class AndCondition(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class AndChainCondition(val expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class OrCondition(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class OrChainCondition(val expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class NotCondition(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class EqualCondition<T : AnyValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class NotEqualCondition<T : AnyValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class GreaterThanCondition<T : NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class GreaterOrEqualThanCondition<T : NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class LessThanCondition<T : NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class LessOrEqualThanCondition<T : NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class IsNotNullCondition<T : AnyValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class IsNullCondition<T : AnyValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class InListCondition<T : AnyValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val list: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class NotInListCondition<T : AnyValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val list: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class InSubQueryCondition<T : AnyValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val query: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class NotInSubQueryCondition<T : AnyValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val query: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class BetweenCondition<T : NumericValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val lower: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val upper: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

class LikeCondition(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>, val pattern: String) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

