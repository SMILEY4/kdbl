package de.ruegnerlukas.sqldsl.dsl.grammar.expr

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.IntValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType


interface AggregateFunction<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>

class CountAllAggFunction : de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<IntValueType>

class CountAggFunction<T: AnyValueType>(val column: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<IntValueType>

class MaxAggFunction<T: NumericValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<T>

class MinAggFunction<T: NumericValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<T>

class SumAggFunction<T: NumericValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<T>

class AvgAggFunction<T: NumericValueType>(val expression: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>) :
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction<T>