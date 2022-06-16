package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionExpr
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionType
import de.ruegnerlukas.sqldsl.dsl.expression.IntLiteralExpr


object Fn {

	@JvmName("abs_s") fun abs(x: Expr<Short>) = FunctionExpr<Short>(FunctionType.ABS, x)
	@JvmName("abs_i") fun abs(x: Expr<Int>) = FunctionExpr<Int>(FunctionType.ABS, x)
	@JvmName("abs_l") fun abs(x: Expr<Long>) = FunctionExpr<Long>(FunctionType.ABS, x)
	@JvmName("abs_f") fun abs(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ABS, x)
	@JvmName("abs_d") fun abs(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ABS, x)

	@JvmName("ceil_f") fun ceil(x: Expr<Float>) = FunctionExpr<Int>(FunctionType.CEIL, x)
	@JvmName("ceil_d") fun ceil(x: Expr<Double>) = FunctionExpr<Long>(FunctionType.CEIL, x)

	@JvmName("floor_f") fun floor(x: Expr<Float>) = FunctionExpr<Int>(FunctionType.FLOOR, x)
	@JvmName("floor_d") fun floor(x: Expr<Double>) = FunctionExpr<Long>(FunctionType.FLOOR, x)

	@JvmName("round_f") fun round(x: Expr<Float>) = FunctionExpr<Int>(FunctionType.ROUND, x)
	@JvmName("round_d") fun round(x: Expr<Double>) = FunctionExpr<Long>(FunctionType.ROUND, x)

	@JvmName("rad2deg_f") fun toDegrees(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.RAD_TO_DEG, x)
	@JvmName("rad2deg_d") fun toDegrees(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.RAD_TO_DEG, x)

	@JvmName("deg2rad_s") fun toRadians(x: Expr<Short>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_i") fun toRadians(x: Expr<Int>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_l") fun toRadians(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_f") fun toRadians(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_d") fun toRadians(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, x)

	@JvmName("exp_s") fun exp(x: Expr<Short>) = FunctionExpr<Float>(FunctionType.EXPONENTIAL, x)
	@JvmName("exp_i") fun exp(x: Expr<Int>) = FunctionExpr<Float>(FunctionType.EXPONENTIAL, x)
	@JvmName("exp_l") fun exp(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, x)
	@JvmName("exp_f") fun exp(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.EXPONENTIAL, x)
	@JvmName("exp_d") fun exp(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, x)

	@JvmName("ln_s") fun ln(x: Expr<Short>) = FunctionExpr<Float>(FunctionType.LN, x)
	@JvmName("ln_i") fun ln(x: Expr<Int>) = FunctionExpr<Float>(FunctionType.LN, x)
	@JvmName("ln_l") fun ln(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.LN, x)
	@JvmName("ln_f") fun ln(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.LN, x)
	@JvmName("ln_d") fun ln(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.LN, x)

	@JvmName("log_s") fun log(base: Expr<Short>, x: Expr<Short>) = FunctionExpr<Float>(FunctionType.LOG, base, x)
	@JvmName("log_i") fun log(base: Expr<Int>, x: Expr<Int>) = FunctionExpr<Float>(FunctionType.LOG, base, x)
	@JvmName("log_l") fun log(base: Expr<Long>, x: Expr<Long>) = FunctionExpr<Double>(FunctionType.LOG, base, x)
	@JvmName("log_f") fun log(base: Expr<Float>, x: Expr<Float>) = FunctionExpr<Float>(FunctionType.LOG, base, x)
	@JvmName("log_d") fun log(base: Expr<Double>, x: Expr<Double>) = FunctionExpr<Double>(FunctionType.LOG, base, x)

	@JvmName("log_s") fun log(base: Int, x: Expr<Short>) = FunctionExpr<Float>(FunctionType.LOG, IntLiteralExpr(base), x)
	@JvmName("log_i") fun log(base: Int, x: Expr<Int>) = FunctionExpr<Float>(FunctionType.LOG, IntLiteralExpr(base), x)
	@JvmName("log_l") fun log(base: Int, x: Expr<Long>) = FunctionExpr<Double>(FunctionType.LOG, IntLiteralExpr(base), x)
	@JvmName("log_f") fun log(base: Int, x: Expr<Float>) = FunctionExpr<Float>(FunctionType.LOG, IntLiteralExpr(base), x)
	@JvmName("log_d") fun log(base: Int, x: Expr<Double>) = FunctionExpr<Double>(FunctionType.LOG, IntLiteralExpr(base), x)

	@JvmName("mod_s") fun mod(x: Expr<Short>, y: Expr<Short>) = FunctionExpr<Short>(FunctionType.MOD, x, y)
	@JvmName("mod_i") fun mod(x: Expr<Int>, y: Expr<Int>) = FunctionExpr<Int>(FunctionType.MOD, x, y)
	@JvmName("mod_l") fun mod(x: Expr<Long>, y: Expr<Long>) = FunctionExpr<Long>(FunctionType.MOD, x, y)
	@JvmName("mod_f") fun mod(x: Expr<Float>, y: Expr<Float>) = FunctionExpr<Float>(FunctionType.MOD, x, y)
	@JvmName("mod_d") fun mod(x: Expr<Double>, y: Expr<Double>) = FunctionExpr<Double>(FunctionType.MOD, x, y)

	@JvmName("mod_s") fun mod(x: Expr<Short>, y: Int) = FunctionExpr<Short>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_i") fun mod(x: Expr<Int>, y: Int) = FunctionExpr<Int>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_l") fun mod(x: Expr<Long>, y: Int) = FunctionExpr<Long>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_f") fun mod(x: Expr<Float>, y: Int) = FunctionExpr<Float>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_d") fun mod(x: Expr<Double>, y: Int) = FunctionExpr<Double>(FunctionType.MOD, x, IntLiteralExpr(y))

	fun pi() = FunctionExpr<Double>(FunctionType.PI)

	@JvmName("pow_s") fun pow(x: Expr<Short>, y: Expr<Short>) = FunctionExpr<Short>(FunctionType.POWER, x, y)
	@JvmName("pow_i") fun pow(x: Expr<Int>, y: Expr<Int>) = FunctionExpr<Int>(FunctionType.POWER, x, y)
	@JvmName("pow_l") fun pow(x: Expr<Long>, y: Expr<Long>) = FunctionExpr<Long>(FunctionType.POWER, x, y)
	@JvmName("pow_f") fun pow(x: Expr<Float>, y: Expr<Float>) = FunctionExpr<Float>(FunctionType.POWER, x, y)
	@JvmName("pow_d") fun pow(x: Expr<Double>, y: Expr<Double>) = FunctionExpr<Double>(FunctionType.POWER, x, y)

	@JvmName("pow_s") fun pow(x: Expr<Short>, y: Int) = FunctionExpr<Short>(FunctionType.POWER, x, IntLiteralExpr(y))
	@JvmName("pow_i") fun pow(x: Expr<Int>, y: Int) = FunctionExpr<Int>(FunctionType.POWER, x, IntLiteralExpr(y))
	@JvmName("pow_l") fun pow(x: Expr<Long>, y: Int) = FunctionExpr<Long>(FunctionType.POWER, x, IntLiteralExpr(y))
	@JvmName("pow_f") fun pow(x: Expr<Float>, y: Int) = FunctionExpr<Float>(FunctionType.POWER, x, IntLiteralExpr(y))
	@JvmName("pow_d") fun pow(x: Expr<Double>, y: Int) = FunctionExpr<Double>(FunctionType.POWER, x, IntLiteralExpr(y))

	@JvmName("sqrt_s") fun sqrt(x: Expr<Short>) = FunctionExpr<Float>(FunctionType.SQRT, x)
	@JvmName("sqrt_i") fun sqrt(x: Expr<Int>) = FunctionExpr<Float>(FunctionType.SQRT, x)
	@JvmName("sqrt_l") fun sqrt(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.SQRT, x)
	@JvmName("sqrt_f") fun sqrt(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.SQRT, x)
	@JvmName("sqrt_d") fun sqrt(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.SQRT, x)

	@JvmName("min_s") fun min(x: List<Expr<Short>>) = FunctionExpr<Short>(FunctionType.MIN, x)
	@JvmName("min_i") fun min(x: List<Expr<Int>>) = FunctionExpr<Int>(FunctionType.MIN, x)
	@JvmName("min_l") fun min(x: List<Expr<Long>>) = FunctionExpr<Long>(FunctionType.MIN, x)
	@JvmName("min_f") fun min(x: List<Expr<Float>>) = FunctionExpr<Float>(FunctionType.MIN, x)
	@JvmName("min_d") fun min(x: List<Expr<Double>>) = FunctionExpr<Double>(FunctionType.MIN, x)

	@JvmName("min_s") fun min(vararg x: Expr<Short>) = FunctionExpr<Short>(FunctionType.MIN, x.toList())
	@JvmName("min_i") fun min(vararg x: Expr<Int>) = FunctionExpr<Int>(FunctionType.MIN, x.toList())
	@JvmName("min_l") fun min(vararg x: Expr<Long>) = FunctionExpr<Long>(FunctionType.MIN, x.toList())
	@JvmName("min_f") fun min(vararg x: Expr<Float>) = FunctionExpr<Float>(FunctionType.MIN, x.toList())
	@JvmName("min_d") fun min(vararg x: Expr<Double>) = FunctionExpr<Double>(FunctionType.MIN, x.toList())

	@JvmName("max_s") fun max(x: List<Expr<Short>>) = FunctionExpr<Short>(FunctionType.MAX, x)
	@JvmName("max_i") fun max(x: List<Expr<Int>>) = FunctionExpr<Int>(FunctionType.MAX, x)
	@JvmName("max_l") fun max(x: List<Expr<Long>>) = FunctionExpr<Long>(FunctionType.MAX, x)
	@JvmName("max_f") fun max(x: List<Expr<Float>>) = FunctionExpr<Float>(FunctionType.MAX, x)
	@JvmName("max_d") fun max(x: List<Expr<Double>>) = FunctionExpr<Double>(FunctionType.MAX, x)

	@JvmName("max_s") fun max(vararg x: Expr<Short>) = FunctionExpr<Short>(FunctionType.MAX, x.toList())
	@JvmName("max_i") fun max(vararg x: Expr<Int>) = FunctionExpr<Int>(FunctionType.MAX, x.toList())
	@JvmName("max_l") fun max(vararg x: Expr<Long>) = FunctionExpr<Long>(FunctionType.MAX, x.toList())
	@JvmName("max_f") fun max(vararg x: Expr<Float>) = FunctionExpr<Float>(FunctionType.MAX, x.toList())
	@JvmName("max_d") fun max(vararg x: Expr<Double>) = FunctionExpr<Double>(FunctionType.MAX, x.toList())

	fun random() = FunctionExpr<Long>(FunctionType.RANDOM)



}

//
///**
// * A function that returns the absolute value of the argument or "NULL" if the value is "NULL"
// */
//@JvmName("abss")
//fun abs(x: Expr<Short>) = FunctionExpr<Short>(FunctionType.ABS, v)
//
//
///**
// * A function that returns the absolute value of the argument or "NULL" if the value is "NULL"
// */
//@JvmName("absi")
//fun abs(x: Expr<Int>) = FunctionExpr<Int>(FunctionType.ABS, v)
//
//
///**
// * A function that returns the absolute value of the argument or "NULL" if the value is "NULL"
// */
//@JvmName("absl")
//fun abs(x: Expr<Long>) = FunctionExpr<Long>(FunctionType.ABS, v)
//
//
///**
// * A function that returns the absolute value of the argument or "NULL" if the value is "NULL"
// */
//@JvmName("absf")
//fun abs(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ABS, v)
//
//
///**
// * A function that returns the absolute value of the argument or "NULL" if the value is "NULL"
// */
//@JvmName("absd")
//fun abs(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ABS, v)
//
//
///**
// * A function that returns the first integer greater than or equal to the given value
// */
//@JvmName("ceilf")
//fun ceil(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.CEIL, v)
//
//
///**
// * A function that returns the first integer greater than or equal to the given value
// */
//@JvmName("ceild")
//fun ceil(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.CEIL, v)
//
//
///**
// * A function that returns the first integer smaller than or equal to the given value
// */
//@JvmName("floorf")
//fun floor(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.FLOOR, v)
//
//
///**
// * A function that returns the first integer smaller than or equal to the given value
// */
//@JvmName("floord")
//fun floor(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.FLOOR, v)
//
//
///**
// * A function that returns the given value from radians to degrees
// */
//@JvmName("rad2degf")
//fun degrees(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.RAD_TO_DEG, v)
//
//
///**
// * A function that returns the given value from radians to degrees
// */
//@JvmName("rad2degd")
//fun degrees(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.RAD_TO_DEG, v)
//
//
///**
// * A function that returns the given value from degrees to radians
// */
//@JvmName("def2radf")
//fun radians(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, v)
//
//
///**
// * A function that returns the given value from degrees to radians
// */
//@JvmName("deg2radd")
//fun radians(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, v)
//
//
///**
// * A function that returns e (Euler's number) raised to the power of the given value
// */
//@JvmName("exps")
//fun exp(x: Expr<Short>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns e (Euler's number) raised to the power of the given value
// */
//@JvmName("expi")
//fun exp(x: Expr<Int>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns e (Euler's number) raised to the power of the given value
// */
//@JvmName("expl")
//fun exp(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns e (Euler's number) raised to the power of the given value
// */
//@JvmName("expf")
//fun exp(x: Expr<Float>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns e (Euler's number) raised to the power of the given value
// */
//@JvmName("expd")
//fun exp(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns the natural logarithm of the given value
// */
//@JvmName("lns")
//fun ln(x: Expr<Short>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns the natural logarithm of the given value
// */
//@JvmName("lni")
//fun ln(x: Expr<Int>) = FunctionExpr<Double>(FunctionType.LN, v)
//
//
///**
// * A function that returns the natural logarithm of the given value
// */
//@JvmName("lnl")
//fun ln(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.LN, v)
//
//
///**
// * A function that returns the natural logarithm of the given value
// */
//@JvmName("lnf")
//fun ln(x: Expr<Float>) = FunctionExpr<Double>(FunctionType.LN, v)
//
//
///**
// * A function that returns the natural logarithm of the given value
// */
//@JvmName("lnd")
//fun ln(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.LN, v)
//
//
//
//
//
///**
// * A function that returns the natural logarithm of the given value for the given base
// */
//@JvmName("logs")
//fun log(x: Expr<Short>) = FunctionExpr<Double>(FunctionType.EXPONENTIAL, v)
//
//
///**
// * A function that returns the logarithm of the given value for the given base
// */
//@JvmName("logi")
//fun log(x: Expr<Int>) = FunctionExpr<Double>(FunctionType.LOG, v)
//
//
///**
// * A function that returns the natural logarithm of the given value for the given base
// */
//@JvmName("logl")
//fun log(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.LOG, v)
//
//
///**
// * A function that returns the natural logarithm of the given value for the given base
// */
//@JvmName("logf")
//fun log(x: Expr<Float>) = FunctionExpr<Double>(FunctionType.LOG, v)
//
//
///**
// * A function that returns the natural logarithm of the given value for the given base
// */
//@JvmName("logd")
//fun log(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.LOG, v)
//
//
//
//
//
//
///**
// * A function that returns a hex-string of the given argument
// */
//fun Expr<*>.hex() = FunctionExpr<String>(FunctionType.TO_HEX, this)
//
//
///**
// * A function that returns the length of the given string
// */
//fun Expr<String>.length() = FunctionExpr<Int>(FunctionType.LENGTH, this)
//
//
///**
// * A function that returns the given string with all characters converted to lower case
// */
//fun Expr<String>.lower() = FunctionExpr<String>(FunctionType.LOWER, this)
//
//
///**
// * A function that returns the given string with all characters converted to upper case
// */
//fun Expr<String>.upper() = FunctionExpr<String>(FunctionType.UPPER, this)
//
//
///**
// * A function that returns the given string with all whitespaces starting from the left side removed
// */
//fun Expr<String>.ltrim() = FunctionExpr<String>(FunctionType.LTRIM, this)
//
//
///**
// * A function that returns the given string with all whitespaces starting from the right side removed
// */
//fun Expr<String>.rtrim() = FunctionExpr<String>(FunctionType.RTRIM, this)
//
//
///**
// * A function that returns the given string with all whitespaces starting from the right and left side removed
// */
//fun Expr<String>.trim() = FunctionExpr<String>(FunctionType.TRIM, this)
//
//
///**
// * A function that returns the maximum value from the given values
// */
//fun <T> max(values: List<Expr<T>>) = FunctionExpr<T>(FunctionType.MAX, values)
//
//
///**
// * A function that returns the maximum value from the given values
// */
//fun <T> max(vararg values: Expr<T>) = FunctionExpr<T>(FunctionType.MAX, values.toList())
//
//
///**
// * A function that returns the minimum value from the given values
// */
//fun <T> min(values: List<Expr<T>>) = FunctionExpr<T>(FunctionType.MIN, values)
//
//
///**
// * A function that returns the minimum value from the given values
// */
//fun <T> min(vararg values: Expr<T>) = FunctionExpr<T>(FunctionType.MIN, values.toList())
//
//
///**
// * A function that returns a random positive or negative integer-value
// */
//fun random() = FunctionExpr<Short>(FunctionType.RANDOM)
//
//
///**
// * A function that returns the string with all occurrences of [oldValue] replaced with [newValue]
// */
//fun Expr<String>.replace(oldValue: Expr<String>, newValue: Expr<String>) =
//	FunctionExpr<String>(FunctionType.REPLACE, this, oldValue, newValue)
//
//
///**
// * A function that returns the string with all occurrences of [oldValue] replaced with [newValue]
// */
//fun Expr<String>.replace(oldValue: String, newValue: String) =
//	FunctionExpr<Short>(FunctionType.REPLACE, this, StringLiteralExpr(oldValue), StringLiteralExpr(newValue))
//
//
///**
// * A function that returns the given value rounded to the nearest integer
// */
//@JvmName("roundf")
//fun Expr<Float>.round() = FunctionExpr<Int>(FunctionType.ROUND, this)
//
//
///**
// * A function that returns the given value rounded to the nearest integer
// */
//@JvmName("roundd")
//fun Expr<Double>.round() = FunctionExpr<Long>(FunctionType.ROUND, this)
//
//
///**
// * A function that returns the sign of the given number, either "-1", "0" or "+1"
// */
//@JvmName("signs")
//fun Expr<Short>.sign() = FunctionExpr<Int>(FunctionType.SIGN, this)
//
//
///**
// * A function that returns the sign of the given number, either "-1", "0" or "+1"
// */
//@JvmName("signi")
//fun Expr<Int>.sign() = FunctionExpr<Int>(FunctionType.SIGN, this)
//
//
///**
// * A function that returns the sign of the given number, either "-1", "0" or "+1"
// */
//@JvmName("signl")
//fun Expr<Long>.sign() = FunctionExpr<Int>(FunctionType.SIGN, this)
//
//
///**
// * A function that returns the sign of the given number, either "-1", "0" or "+1"
// */
//@JvmName("signf")
//fun Expr<Float>.sign() = FunctionExpr<Int>(FunctionType.SIGN, this)
//
//
///**
// * A function that returns the sign of the given number, either "-1", "0" or "+1"
// */
//@JvmName("signd")
//fun Expr<Double>.sign() = FunctionExpr<Int>(FunctionType.SIGN, this)
//
//
///**
// * A function that returns a substring of the given string, starting at the [start]-index and with a given [length].
// * The first character is (start-)index "1". If the start-index is negative, the substring is found by counting from the right.
// */
//fun Expr<String>.substring(start: Expr<Int>, length: Expr<Int>) = FunctionExpr<String>(FunctionType.SUBSTRING, this, start, length)
//
//
///**
// * A function that returns a substring of the given string, starting at the [start]-index and with a given [length].
// * The first character is (start-)index "1". If the start-index is negative, the substring is found by counting from the right.
// */
//fun Expr<String>.substring(start: Int, length: Int) =
//	FunctionExpr<String>(FunctionType.SUBSTRING, this, IntLiteralExpr(start), IntLiteralExpr(length))
//
//
//fun countAll() = FunctionExpr<Int>(FunctionType.AGG_COUNT_ALL)
//
//fun countAllDistinct() = FunctionExpr<Int>(FunctionType.AGG_COUNT_ALL_DISTINCT)
//
//fun <T> Expr<T>.count() = FunctionExpr<Int>(FunctionType.AGG_COUNT, this)
//
//fun <T> Expr<T>.countDistinct() = FunctionExpr<Int>(FunctionType.AGG_COUNT_DISTINCT, this)
//
//fun <T> Expr<T>.min() = FunctionExpr<T>(FunctionType.AGG_MIN, this)
//
//fun <T> Expr<T>.max() = FunctionExpr<T>(FunctionType.AGG_MAX, this)
//
//fun <T> Expr<T>.sum() = FunctionExpr<T>(FunctionType.AGG_SUM, this)