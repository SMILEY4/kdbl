package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.schema.AnyValueType
import de.ruegnerlukas.sqldsl2.schema.IntValueType
import de.ruegnerlukas.sqldsl2.schema.NumericValueType


interface AggregateFunction<T: AnyValueType> : Expr<T>

class CountAllAggFunction : AggregateFunction<IntValueType>

class CountAggFunction<T: AnyValueType>(val column: ColumnExpr<T>) : AggregateFunction<IntValueType>

class MaxAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>

class MinAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>

class SumAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>

class AvgAggFunction<T: NumericValueType>(val expression: Expr<T>) : AggregateFunction<T>