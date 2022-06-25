package de.ruegnerlukas.kdbl.dsl.expression

import de.ruegnerlukas.kdbl.dsl.statements.SelectElement

/**
 * An expression that can result in any value
 */
interface Expr<out T> : SelectElement


/**
 * create an alias that can be assigned another expression
 */
class AliasExpr<T>(val alias: String) : Expr<T> {

	private var expr: Expr<T>? = null

	constructor(expr: Expr<T>, alias: String) : this(alias) {
		assign(expr)
	}


	/**
	 * assign the given expression to this alias
	 */
	fun assign(expr: Expr<T>): AliasExpr<T> {
		this.expr = expr
		return this
	}


	/**
	 * @return the expression assigned to this alias
	 */
	fun getExpr() = expr ?: throw IllegalStateException("Content of alias-expr is null")

}


/**
 * Another query as an expression
 */
interface SubQueryExpr<T> : Expr<T>


/**
 * An expression that converts the given expression that results in type [S] to one that results in type [T]. Does not affect the sql-string
 */
class TypecastExpr<S, T>(val expr: Expr<S>) : Expr<T>