package de.ruegnerlukas.sqldsl.dsl.expression

/**
 * The possible functions
 */
enum class FunctionType {
	ABS,
	CEIL,
	FLOOR,
	ROUND,
	RAD_TO_DEG,
	DEG_TO_RAD,
	EXPONENTIAL,
	LN,
	LOG,
	MOD,
	PI,
	POWER,
	SIGN,
	SQRT,
	MAX,
	MIN,

	RANDOM,

	ACOS,
	ASIN,
	ATAN,
	ATAN2,
	SIN,
	COS,
	TAN,

	SINH,
	COSH,
	TANH,
	ASINH,
	ACOSH,
	ATANH,

	CONCAT,
	LENGTH,
	LOWER,
	UPPER,
	SUBSTRING,
	TRIM,
	RTRIM,
	LTRIM,
	REPLACE,
	TO_HEX,

	AGG_COUNT_ALL,
	AGG_COUNT_ALL_DISTINCT,
	AGG_COUNT,
	AGG_COUNT_DISTINCT,
	AGG_MIN,
	AGG_MAX,
	AGG_SUM, // if contains null => result = null
	AGG_TOTAL, // sum of all non-null-values
	AGG_CONCAT,

}


/**
 * A build-in function as an expression
 */
class FunctionExpr<T>(val type: FunctionType, val arguments: List<Expr<*>>) : Expr<T> {

	constructor(fnType: FunctionType, vararg fnArguments: Expr<*>) : this(fnType, fnArguments.toList())

}