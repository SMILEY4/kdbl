package de.ruegnerlukas.sqldsl2.grammar.expr

interface OperationExpr : Expr

class AddOperation(val left: Expr, val right: Expr) : OperationExpr

class SubOperation(val left: Expr, val right: Expr) : OperationExpr

class ConcatOperation(val left: Expr, val right: Expr) : OperationExpr
