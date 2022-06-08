package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.expr.AvgAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.grammar.expr.CountAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.Expr
import de.ruegnerlukas.sqldsl.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.MinAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.NumericValueType

fun countAll() = CountAllAggFunction()
fun <T : AnyValueType> ColumnExpr<T>.count() = CountAggFunction(this)
fun <T : NumericValueType> Expr<T>.max() = MaxAggFunction(this)
fun <T : NumericValueType> Expr<T>.min() = MinAggFunction(this)
fun <T : NumericValueType> Expr<T>.sum() = SumAggFunction(this)
fun <T : NumericValueType> Expr<T>.avg() = AvgAggFunction(this)


