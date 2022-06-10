package de.ruegnerlukas.sqldsl.dsl.grammar.expr

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.StringValueType

interface OperationExpr<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>

class AddOperation<T: NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<T>

class AddAllOperation<T: NumericValueType>(val expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<T>

class SubOperation<T: NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<T>

class MulOperation<T: NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<T>

class DivOperation<T: NumericValueType>(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<T>

class ConcatOperation(val left: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>, val right: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<StringValueType>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.OperationExpr<StringValueType>
