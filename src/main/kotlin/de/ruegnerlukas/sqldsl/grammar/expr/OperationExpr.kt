package de.ruegnerlukas.sqldsl.grammar.expr

import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType
import de.ruegnerlukas.sqldsl.schema.StringValueType

interface OperationExpr<T: AnyValueType> : Expr<T>

class AddOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class AddAllOperation<T: NumericValueType>(val expressions: List<Expr<T>>) : OperationExpr<T>

class SubOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class MulOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class DivOperation<T: NumericValueType>(val left: Expr<T>, val right: Expr<T>) : OperationExpr<T>

class ConcatOperation(val left: Expr<StringValueType>, val right: Expr<StringValueType>) : OperationExpr<StringValueType>
