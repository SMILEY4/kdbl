package de.ruegnerlukas.sqldsl2.grammar.expr

interface OperationExpr : Expr

class AddOperation(val left: Expr, val right: Expr) : OperationExpr

class AddAllOperation(val expressions: List<Expr>) : OperationExpr

class SubOperation(val left: Expr, val right: Expr) : OperationExpr

class MulOperation(val left: Expr, val right: Expr) : OperationExpr

class DivOperation(val left: Expr, val right: Expr) : OperationExpr

class ConcatOperation(val left: Expr, val right: Expr) : OperationExpr
