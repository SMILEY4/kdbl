package de.ruegnerlukas.sqldsl.grammar.expr

import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.IntValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType


interface AggregateFunction<T: AnyValueType> : Expr<T>

class CountAllAggFunction : AggregateFunction<IntValueType>

class CountAggFunction<T: AnyValueType>(val column: ColumnExpr<T>) : AggregateFunction<IntValueType>

class MaxAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>

class MinAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>

class SumAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>

class AvgAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>