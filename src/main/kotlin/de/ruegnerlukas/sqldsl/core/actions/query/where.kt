package de.ruegnerlukas.sqldsl.core.actions.query


interface QueryExp

//===== ARITHMETIC OPERATORS =====//

//===== COMPARISON OPERATORS =====//

enum class ComparisonOp { EQ, NEQ, GT, GTE, LT, LTE }
class ComparisonQueryExp(val op: ComparisonOp, val first: QueryExp, val second: QueryExp) : QueryExp

fun eq(first: QueryExp, second: QueryExp) = ComparisonQueryExp(ComparisonOp.EQ, first, second)
fun neq(first: QueryExp, second: QueryExp) = ComparisonQueryExp(ComparisonOp.NEQ, first, second)
fun gt(first: QueryExp, second: QueryExp) = ComparisonQueryExp(ComparisonOp.GT, first, second)
fun gte(first: QueryExp, second: QueryExp) = ComparisonQueryExp(ComparisonOp.GTE, first, second)
fun lt(first: QueryExp, second: QueryExp) = ComparisonQueryExp(ComparisonOp.LT, first, second)
fun lte(first: QueryExp, second: QueryExp) = ComparisonQueryExp(ComparisonOp.LTE, first, second)

infix fun AttributeExp.eq(other: QueryExp) = eq(this, other)
infix fun AttributeExp.neq(other: QueryExp) = neq(this, other)
infix fun AttributeExp.gt(other: QueryExp) = gt(this, other)
infix fun AttributeExp.gte(other: QueryExp) = gte(this, other)
infix fun AttributeExp.lt(other: QueryExp) = lt(this, other)
infix fun AttributeExp.lte(other: QueryExp) = lte(this, other)

//====== LOGICAL OPERATORS =======//

enum class LogicalOp {
	EXISTS,
	NOT,
	IS_NULL,

	AND,
	OR,
	IN,
	NOT_IN,
	LIKE,
	CONCAT,

	BETWEEN,
}

open class LogicalQueryExp(val op: LogicalOp) : QueryExp
open class UnaryLogicalQueryExp(op: LogicalOp, val expr: QueryExp) : LogicalQueryExp(op)
open class BinaryLogicalQueryExp(op: LogicalOp, val first: QueryExp, val second: QueryExp) : LogicalQueryExp(op)
open class BetweenLogicalQueryExp(op: LogicalOp, val value: QueryExp, val min: QueryExp, val max: QueryExp) : LogicalQueryExp(op)

fun exists(exp: QueryExp) = UnaryLogicalQueryExp(LogicalOp.EXISTS, exp)
fun not(exp: QueryExp) = UnaryLogicalQueryExp(LogicalOp.NOT, exp)
fun isNull(exp: QueryExp) = UnaryLogicalQueryExp(LogicalOp.IS_NULL, exp)

fun and(first: QueryExp, second: QueryExp) = BinaryLogicalQueryExp(LogicalOp.AND, first, second)
fun or(first: QueryExp, second: QueryExp) = BinaryLogicalQueryExp(LogicalOp.OR, first, second)
fun isIn(first: QueryExp, second: QueryExp) = BinaryLogicalQueryExp(LogicalOp.IN, first, second)
fun isNotIn(first: QueryExp, second: QueryExp) = BinaryLogicalQueryExp(LogicalOp.NOT_IN, first, second)
fun like(first: QueryExp, second: QueryExp) = BinaryLogicalQueryExp(LogicalOp.LIKE, first, second)
fun concat(first: QueryExp, second: QueryExp) = BinaryLogicalQueryExp(LogicalOp.CONCAT, first, second)

fun between(first: QueryExp, second: QueryExp, third: QueryExp) = BetweenLogicalQueryExp(LogicalOp.BETWEEN, first, second, third)

//========== ATTRIBUTES ==========//

interface AttributeExp : QueryExp

interface ColumnAttributeExp : AttributeExp

interface ConstAttributeExp : AttributeExp {
	fun valueAsString(): String
}


class StringConstAttributeExp(private val value: String) : ConstAttributeExp {
	override fun valueAsString() = "\"$value\""
}

class IntegerConstAttributeExp(private val value: Int) : ConstAttributeExp {
	override fun valueAsString() = "$value"
}

class BooleanConstAttributeExp(private val value: Boolean) : ConstAttributeExp {
	override fun valueAsString() = if(value) "True" else "False"
}


fun const(value: String) = StringConstAttributeExp(value)
fun const(value: Int) = IntegerConstAttributeExp(value)
fun const(value: Boolean) = BooleanConstAttributeExp(value)

