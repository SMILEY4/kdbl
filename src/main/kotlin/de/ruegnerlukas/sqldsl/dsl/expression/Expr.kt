package de.ruegnerlukas.sqldsl.dsl.expression

import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement

interface Expr<T> : SelectElement

class AliasExpr<T>(val alias: String) : Expr<T> {

	private var expr: Expr<T>? = null

	constructor(expr: Expr<T>, alias: String) : this(alias) {
		assign(expr)
	}

	fun assign(expr: Expr<T>): AliasExpr<T> {
		this.expr = expr
		return this
	}

	fun getExpr() = expr ?: throw IllegalStateException("Content of alias-expr is null")

}

interface SubQueryExpr<T> : Expr<T>


class TypecastExpr<S, T>(val expr: Expr<S>) : Expr<T>