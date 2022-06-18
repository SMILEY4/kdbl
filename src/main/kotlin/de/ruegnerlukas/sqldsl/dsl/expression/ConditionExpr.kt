package de.ruegnerlukas.sqldsl.dsl.expression

import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement

/**
 * An expression with a "true" or "false"-result
 */
interface ConditionExpr : Expr<Boolean>

class EqualExpr<T>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr
class NotEqualExpr<T>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class LessThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr
class LessEqualThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr
class GreaterThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr
class GreaterEqualThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : ConditionExpr

class NotNullExpr(val expr: Expr<*>) : ConditionExpr
class NullExpr(val expr: Expr<*>) : ConditionExpr

class LikeExpr(val value: Expr<String>, val pattern: String) : ConditionExpr
class BetweenExpr<T>(val value: Expr<T>, val lower: Expr<T>, val upper: Expr<T>) : ConditionExpr

class InListExpr<T>(val expr: Expr<*>, val list: ListLiteralExpr<T>) : ConditionExpr
class NotInListExpr<T>(val expr: Expr<*>, val list: ListLiteralExpr<T>) : ConditionExpr

class InQueryExpr<T>(val expr: Expr<*>, val query: QueryStatement<T>) : ConditionExpr
class NotInQueryExpr<T>(val expr: Expr<*>, val query: QueryStatement<T>) : ConditionExpr

class NotExpr<T>(val value: Expr<T>) : ConditionExpr

class AndExpr(val left: Expr<Boolean>, val right: Expr<Boolean>) : ConditionExpr
class OrExpr(val left: Expr<Boolean>, val right: Expr<Boolean>) : ConditionExpr

class AndChainExpr(val expressions: List<Expr<Boolean>>) : ConditionExpr
class OrChainExpr(val expressions: List<Expr<Boolean>>) : ConditionExpr
