package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.having.ConditionHavingExpression
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.where.ConditionWhereExpression

interface ConditionExpr : OperationExpr, ConditionWhereExpression, ConditionHavingExpression

class AndCondition(val left: ConditionExpr, val right: ConditionExpr) : ConditionExpr

class AndChainCondition(val expressions: List<ConditionExpr>) : ConditionExpr

class OrCondition(val left: ConditionExpr, val right: ConditionExpr) : ConditionExpr

class OrChainCondition(val expressions: List<ConditionExpr>) : ConditionExpr

class EqualCondition(val left: Expr, val right: Expr) : ConditionExpr

class NotEqualCondition(val left: Expr, val right: Expr) : ConditionExpr

class GreaterThanCondition(val left: Expr, val right: Expr) : ConditionExpr

class GreaterOrEqualThanCondition(val left: Expr, val right: Expr) : ConditionExpr

class LessThanCondition(val left: Expr, val right: Expr) : ConditionExpr

class NotCondition(val value: ConditionExpr) : ConditionExpr

class IsNotNullCondition(val value: Expr) : ConditionExpr

class IsNullCondition(val value: Expr) : ConditionExpr

class InListCondition(val value: Expr, val list: ListLiteral) : ConditionExpr

class NotInListCondition(val value: Expr, val list: ListLiteral) : ConditionExpr

class InSubQueryCondition(val value: Expr, val query: QueryStatement) : ConditionExpr

class NotInSubQueryCondition(val value: Expr, val query: QueryStatement) : ConditionExpr

class BetweenCondition(val value: Expr, val lower: Expr, val upper: Expr) : ConditionExpr

class LikeCondition(val value: Expr, val pattern: String) : ConditionExpr

