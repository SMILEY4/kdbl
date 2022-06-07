package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.schema.AnyValueType
import de.ruegnerlukas.sqldsl2.schema.NumericValueType
import de.ruegnerlukas.sqldsl2.schema.StringValueType

interface OperationExpr<T: AnyValueType> : Expr<T>

class AddOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class AddAllOperation<T: NumericValueType>(val expressions: List<Expr<T>>) : OperationExpr<T>

class SubOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class MulOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class DivOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class ConcatOperation(val left: Expr<StringValueType>, val right: Expr<StringValueType>) : OperationExpr<StringValueType>
