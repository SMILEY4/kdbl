package de.ruegnerlukas.sqldsl.dsl.expression

/*
POSTGRES
https://www.postgresql.org/docs/12/functions-math.html
https://www.postgresql.org/docs/12/functions-string.html
https://www.postgresql.org/docs/12/functions-binarystring.html
https://www.postgresql.org/docs/12/functions-aggregate.html
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
	COST,
	COT,
	SIN,
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
	POSITION,
	SUBSTRING,
	TRIM,
	RTRIM,
	LTRIM,
	TO_BASE64,
	FROM_BASE64,
	REPLACE,
	TO_HEX,

	AGG_COUNT_ALL,
	AGG_COUNT_ALL_DISTINCT,
	AGG_COUNT,
	AGG_COUNT_DISTINCT,
	AGG_MIN,
	AGG_MAX,
	AGG_SUM,
	AGG_CONCAT,

}

class FunctionExpr<T>(val type: FunctionType, val arguments: List<Expr<*>>) : Expr<T> {

	constructor(fnType: FunctionType, vararg fnArguments: Expr<*>) : this(fnType, fnArguments.toList())

}