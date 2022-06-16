package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expression.AliasExpr
import de.ruegnerlukas.sqldsl.dsl.expression.DerivedTable
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.TypecastExpr
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement

fun <T> Expr<T>.alias(alias: String) = AliasExpr(this, alias)
fun <T> Expr<T>.alias(aliasExpr: AliasExpr<T>) = aliasExpr.assign(this)

fun FromElement.assign(table: DerivedTable): DerivedTable {
	table.assign(this)
	return table
}

@JvmName("sCastInt")fun Expr<Short>.int() = TypecastExpr<Short,Int>(this)
@JvmName("sCastLong")fun Expr<Short>.long() = TypecastExpr<Short,Long>(this)
@JvmName("iCastLong") fun Expr<Int>.long() = TypecastExpr<Int,Long>(this)
@JvmName("fCastDouble") fun Expr<Float>.double() = TypecastExpr<Float,Double>(this)
