package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.having.ConditionHavingExpression
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateWhereStatement
import de.ruegnerlukas.sqldsl2.grammar.where.ConditionWhereExpression

interface ConditionExpr : OperationExpr, ConditionWhereExpression, ConditionHavingExpression, UpdateWhereStatement

class AndCondition(val left: ConditionExpr, val right: ConditionExpr) : ConditionExpr

class AndChainCondition(val expressions: List<ConditionExpr>) : ConditionExpr

class OrCondition(val left: ConditionExpr, val right: ConditionExpr) : ConditionExpr

class OrChainCondition(val expressions: List<ConditionExpr>) : ConditionExpr

class EqualCondition(val left: Expr, val right: Expr) : ConditionExpr

class NotEqualCondition(val left: Expr, val right: Expr) : ConditionExpr

class GreaterThanCondition(val left: Expr, val right: Expr) : ConditionExpr

class GreaterOrEqualThanCondition(val left: Expr, val right: Expr) : ConditionExpr

class LessThanCondition(val left: Expr, val right: Expr) : ConditionExpr

class NotCondition(val expression: ConditionExpr) : ConditionExpr

class IsNotNullCondition(val expression: Expr) : ConditionExpr

class IsNullCondition(val expression: Expr) : ConditionExpr

class InListCondition(val expression: Expr, val list: ListLiteral) : ConditionExpr

class NotInListCondition(val expression: Expr, val list: ListLiteral) : ConditionExpr

class InSubQueryCondition(val expression: Expr, val query: QueryStatement) : ConditionExpr

class NotInSubQueryCondition(val expression: Expr, val query: QueryStatement) : ConditionExpr

class BetweenCondition(val expression: Expr, val lower: Expr, val upper: Expr) : ConditionExpr

class LikeCondition(val expression: Expr, val pattern: String) : ConditionExpr

