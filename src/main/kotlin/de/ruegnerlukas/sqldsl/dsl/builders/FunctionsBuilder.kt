package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NumericValueType

fun countAll() = de.ruegnerlukas.sqldsl.dsl.grammar.expr.CountAllAggFunction()
fun <T : AnyValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<T>.count() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.CountAggFunction(this)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.max() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.MaxAggFunction(this)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.min() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.MinAggFunction(this)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.sum() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.SumAggFunction(this)
fun <T : NumericValueType> de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<T>.avg() =
	de.ruegnerlukas.sqldsl.dsl.grammar.expr.AvgAggFunction(this)


