package de.ruegnerlukas.sqldsl.dsl.expr

import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement

interface Expr<T> : SelectElement

class EqualExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<Boolean>
class NotEqualExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<Boolean>

class LessThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<Boolean>
class LessEqualThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<Boolean>
class GreaterThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<Boolean>
class GreaterEqualThanExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<Boolean>

class NotNullExpr(val expr: Expr<*>) : Expr<Boolean>
class NullExpr(val expr: Expr<*>) : Expr<Boolean>

class NotExpr<T>(val value: Expr<T>) : Expr<Boolean>
class AndExpr(val left: Expr<Boolean>, val right: Expr<Boolean>) : Expr<Boolean>
class OrExpr(val left: Expr<Boolean>, val right: Expr<Boolean>) : Expr<Boolean>

class AndChainExpr(val expressions: List<Expr<Boolean>>) : Expr<Boolean>
class OrChainExpr(val expressions: List<Expr<Boolean>>) : Expr<Boolean>

class LikeExpr(val value: Expr<String>, val pattern: String) : Expr<Boolean>
class BetweenExpr<T>(val value: Expr<T>, val lower: Expr<T>, val upper: Expr<T>) : Expr<Boolean>

class InListExpr<T>(val expr: Expr<*>, val list: ListLiteralExpr<T>) : Expr<Boolean>
class NotInListExpr<T>(val expr: Expr<*>, val list: ListLiteralExpr<T>) : Expr<Boolean>

class InQueryExpr<T>(val expr: Expr<*>, val query: QueryStatement<T>) : Expr<Boolean>
class NotInQueryExpr<T>(val expr: Expr<*>, val query: QueryStatement<T>) : Expr<Boolean>

class AddExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<T>
class SubExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<T>
class MulExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<T>
class DivExpr<T>(val left: Expr<T>, val right: Expr<T>) : Expr<T>

class AddAllExpr<T>(val expr: List<Expr<T>>) : Expr<T>

class CountAllExpr : Expr<Int>
class CountAllDistinctExpr : Expr<Int>
class CountExpr(val value: Expr<*>) : Expr<Int>
class CountDistinctExpr(val value: Expr<*>) : Expr<Int>
class MinExpr<T>(val value: Expr<T>) : Expr<T>
class MaxExpr<T>(val value: Expr<T>) : Expr<T>
class SumExpr<T>(val value: Expr<T>) : Expr<T>

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

interface LiteralExpr<T> : Expr<T>
class IntLiteralExpr(val value: Int) : LiteralExpr<Int>
class FloatLiteralExpr(val value: Float) : LiteralExpr<Float>
class BooleanLiteralExpr(val value: Boolean) : LiteralExpr<Boolean>
class StringLiteralExpr(val value: String) : LiteralExpr<String>
class ListLiteralExpr<T>(val values: List<Expr<T>>) : LiteralExpr<T>



