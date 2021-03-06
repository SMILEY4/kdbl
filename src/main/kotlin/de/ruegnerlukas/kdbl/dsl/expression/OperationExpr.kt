package de.ruegnerlukas.kdbl.dsl.expression

/**
 * An operation like plus or minus as an expression
 */
interface OperationExpr<T> : Expr<T>

class AddExpr<T>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>
class SubExpr<T>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>
class MulExpr<T>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>
class DivExpr<T>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class AddAllExpr<T>(val expr: List<Expr<T>>) : OperationExpr<T>
