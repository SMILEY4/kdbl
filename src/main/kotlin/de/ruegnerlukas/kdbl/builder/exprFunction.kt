package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.FunctionExpr
import de.ruegnerlukas.kdbl.dsl.expression.FunctionType
import de.ruegnerlukas.kdbl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.kdbl.dsl.expression.StringLiteralExpr


object Fn {

	@JvmName("abs_s") fun abs(x: Expr<Short>) = FunctionExpr<Short>(FunctionType.ABS, x)
	@JvmName("abs_i") fun abs(x: Expr<Int>) = FunctionExpr<Int>(FunctionType.ABS, x)
	@JvmName("abs_l") fun abs(x: Expr<Long>) = FunctionExpr<Long>(FunctionType.ABS, x)
	@JvmName("abs_f") fun abs(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ABS, x)
	@JvmName("abs_d") fun abs(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ABS, x)

	@JvmName("e_abs_s") fun Expr<Short>.abs() = FunctionExpr<Short>(FunctionType.ABS, this)
	@JvmName("e_abs_i") fun Expr<Int>.abs() = FunctionExpr<Int>(FunctionType.ABS, this)
	@JvmName("e_abs_l") fun Expr<Long>.abs() = FunctionExpr<Long>(FunctionType.ABS, this)
	@JvmName("e_abs_f") fun Expr<Float>.abs() = FunctionExpr<Float>(FunctionType.ABS, this)
	@JvmName("e_abs_d") fun Expr<Double>.abs() = FunctionExpr<Double>(FunctionType.ABS, this)

	@JvmName("ceil_f") fun ceil(x: Expr<Float>) = FunctionExpr<Int>(FunctionType.CEIL, x)
	@JvmName("ceil_d") fun ceil(x: Expr<Double>) = FunctionExpr<Long>(FunctionType.CEIL, x)

	@JvmName("e_ceil_f") fun Expr<Float>.ceil() = FunctionExpr<Int>(FunctionType.CEIL, this)
	@JvmName("e_ceil_d") fun Expr<Double>.ceil() = FunctionExpr<Long>(FunctionType.CEIL, this)

	@JvmName("floor_f") fun floor(x: Expr<Float>) = FunctionExpr<Int>(FunctionType.FLOOR, x)
	@JvmName("floor_d") fun floor(x: Expr<Double>) = FunctionExpr<Long>(FunctionType.FLOOR, x)

	@JvmName("e_floor_f") fun Expr<Float>.floor() = FunctionExpr<Int>(FunctionType.FLOOR, this)
	@JvmName("e_floor_d") fun Expr<Double>.floor() = FunctionExpr<Long>(FunctionType.FLOOR, this)


	@JvmName("round_f") fun round(x: Expr<Float>) = FunctionExpr<Int>(FunctionType.ROUND, x)
	@JvmName("round_d") fun round(x: Expr<Double>) = FunctionExpr<Long>(FunctionType.ROUND, x)

	@JvmName("e_round_f") fun Expr<Float>.round() = FunctionExpr<Int>(FunctionType.ROUND)
	@JvmName("e_round_d") fun Expr<Double>.round() = FunctionExpr<Long>(FunctionType.ROUND)

	@JvmName("rad2deg_f") fun toDegrees(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.RAD_TO_DEG, x)
	@JvmName("rad2deg_d") fun toDegrees(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.RAD_TO_DEG, x)

	@JvmName("e_rad2deg_f") fun Expr<Float>.toDegrees() = FunctionExpr<Float>(FunctionType.RAD_TO_DEG, this)
	@JvmName("e_rad2deg_d") fun Expr<Double>.toDegrees() = FunctionExpr<Double>(FunctionType.RAD_TO_DEG, this)

	@JvmName("deg2rad_s") fun toRadians(x: Expr<Short>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_i") fun toRadians(x: Expr<Int>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_l") fun toRadians(x: Expr<Long>) = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_f") fun toRadians(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, x)
	@JvmName("deg2rad_d") fun toRadians(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, x)

	@JvmName("e_deg2rad_s") fun Expr<Short>.toRadians() = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, this)
	@JvmName("e_deg2rad_i") fun Expr<Int>.toRadians() = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, this)
	@JvmName("e_deg2rad_l") fun Expr<Long>.toRadians() = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, this)
	@JvmName("e_deg2rad_f") fun Expr<Float>.toRadians() = FunctionExpr<Float>(FunctionType.DEG_TO_RAD, this)
	@JvmName("e_deg2rad_d") fun Expr<Double>.toRadians() = FunctionExpr<Double>(FunctionType.DEG_TO_RAD, this)

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

	@JvmName("e_mod_s") fun Expr<Short>.mod(y: Expr<Short>) = FunctionExpr<Short>(FunctionType.MOD, this, y)
	@JvmName("e_mod_i") fun Expr<Int>.mod(y: Expr<Int>) = FunctionExpr<Int>(FunctionType.MOD, this, y)
	@JvmName("e_mod_l") fun Expr<Long>.mod(y: Expr<Long>) = FunctionExpr<Long>(FunctionType.MOD, this, y)
	@JvmName("e_mod_f") fun Expr<Float>.mod(y: Expr<Float>) = FunctionExpr<Float>(FunctionType.MOD, this, y)
	@JvmName("e_mod_d") fun Expr<Double>.mod(y: Expr<Double>) = FunctionExpr<Double>(FunctionType.MOD, this, y)

	@JvmName("mod_s") fun mod(x: Expr<Short>, y: Int) = FunctionExpr<Short>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_i") fun mod(x: Expr<Int>, y: Int) = FunctionExpr<Int>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_l") fun mod(x: Expr<Long>, y: Int) = FunctionExpr<Long>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_f") fun mod(x: Expr<Float>, y: Int) = FunctionExpr<Float>(FunctionType.MOD, x, IntLiteralExpr(y))
	@JvmName("mod_d") fun mod(x: Expr<Double>, y: Int) = FunctionExpr<Double>(FunctionType.MOD, x, IntLiteralExpr(y))

	@JvmName("e_mod_s") fun Expr<Short>.mod(y: Int) = FunctionExpr<Short>(FunctionType.MOD, this, IntLiteralExpr(y))
	@JvmName("e_mod_i") fun Expr<Int>.mod(y: Int) = FunctionExpr<Int>(FunctionType.MOD, this, IntLiteralExpr(y))
	@JvmName("e_mod_l") fun Expr<Long>.mod(y: Int) = FunctionExpr<Long>(FunctionType.MOD, this, IntLiteralExpr(y))
	@JvmName("e_mod_f") fun Expr<Float>.mod(y: Int) = FunctionExpr<Float>(FunctionType.MOD, this, IntLiteralExpr(y))
	@JvmName("e_mod_d") fun Expr<Double>.mod(y: Int) = FunctionExpr<Double>(FunctionType.MOD, this, IntLiteralExpr(y))

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


	@JvmName("acos_f") fun acos(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ACOS, x)
	@JvmName("acos_d") fun acos(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ACOS, x)

	@JvmName("asin_f") fun asin(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ASIN, x)
	@JvmName("asin_d") fun asin(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ASIN, x)

	@JvmName("atan_f") fun atan(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ATAN, x)
	@JvmName("atan_d") fun atan(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ATAN, x)

	@JvmName("atan2_f") fun atan2(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ATAN2, x)
	@JvmName("atan2_d") fun atan2(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ATAN2, x)

	@JvmName("sin_f") fun sin(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.SIN, x)
	@JvmName("sin_d") fun sin(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.SIN, x)

	@JvmName("cos_f") fun cos(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.COS, x)
	@JvmName("cos_d") fun cos(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.COS, x)

	@JvmName("tan_f") fun tan(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.TAN, x)
	@JvmName("tan_d") fun tan(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.TAN, x)


	@JvmName("sinh_f") fun sinh(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.SINH, x)
	@JvmName("sinh_d") fun sinh(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.SINH, x)

	@JvmName("cosh_f") fun cosh(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.COSH, x)
	@JvmName("cosh_d") fun cosh(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.COSH, x)

	@JvmName("tanh_f") fun tanh(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.TANH, x)
	@JvmName("tanh_d") fun tanh(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.TANH, x)

	@JvmName("asinh_f") fun asinh(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ASINH, x)
	@JvmName("asinh_d") fun asinh(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ASINH, x)

	@JvmName("acosh_f") fun acosh(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ACOSH, x)
	@JvmName("acosh_d") fun acosh(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ACOSH, x)

	@JvmName("atanh_f") fun atanh(x: Expr<Float>) = FunctionExpr<Float>(FunctionType.ATANH, x)
	@JvmName("atanh_d") fun atanh(x: Expr<Double>) = FunctionExpr<Double>(FunctionType.ATANH, x)


	fun concat(x: Expr<String>, y: Expr<String>) = FunctionExpr<String>(FunctionType.CONCAT, x, y)
	fun concat(x: Expr<String>, y: String) = FunctionExpr<String>(FunctionType.CONCAT, x, StringLiteralExpr(y))
	fun concat(x: String, y: Expr<*>) = FunctionExpr<String>(FunctionType.CONCAT, StringLiteralExpr(x), y)

	@JvmName("e_concat") fun Expr<String>.concat(y: Expr<String>) = FunctionExpr<String>(FunctionType.CONCAT, this, y)
	@JvmName("e_concat") fun Expr<String>.concat(y: String) = FunctionExpr<String>(FunctionType.CONCAT, this, StringLiteralExpr(y))
	@JvmName("e_concat") fun String.concat(y: Expr<String>) = FunctionExpr<String>(FunctionType.CONCAT, StringLiteralExpr(this), y)

	fun length(x: Expr<String>) = FunctionExpr<Int>(FunctionType.LENGTH, x)
	@JvmName("e_length") fun Expr<String>.length() = FunctionExpr<Int>(FunctionType.LENGTH, this)

	fun lower(x: Expr<String>) = FunctionExpr<String>(FunctionType.LOWER, x)
	@JvmName("e_lower") fun Expr<String>.lower() = FunctionExpr<String>(FunctionType.LOWER, this)

	fun upper(x: Expr<String>) = FunctionExpr<String>(FunctionType.UPPER, x)
	@JvmName("e_upper") fun Expr<String>.upper() = FunctionExpr<String>(FunctionType.UPPER, this)

	fun substring(x: Expr<String>, start: Expr<Int>, length: Expr<Int>) = FunctionExpr<String>(FunctionType.SUBSTRING, x, start, length)
	fun substring(x: Expr<String>, start: Int, length: Int) =
		FunctionExpr<String>(FunctionType.SUBSTRING, x, IntLiteralExpr(start), IntLiteralExpr(length))

	@JvmName("e_substr") fun Expr<String>.substring(start: Expr<Int>, length: Expr<Int>) =
		FunctionExpr<String>(FunctionType.SUBSTRING, this, start, length)

	@JvmName("e_substr") fun Expr<String>.substring(start: Int, length: Int) =
		FunctionExpr<String>(FunctionType.SUBSTRING, this, IntLiteralExpr(start), IntLiteralExpr(length))

	fun trim(x: Expr<String>) = FunctionExpr<String>(FunctionType.TRIM, x)
	@JvmName("e_trim") fun Expr<String>.trim() = FunctionExpr<String>(FunctionType.TRIM, this)

	fun rTrim(x: Expr<String>) = FunctionExpr<String>(FunctionType.RTRIM, x)
	@JvmName("e_rtrim") fun Expr<String>.rTrim() = FunctionExpr<String>(FunctionType.RTRIM, this)

	fun lTrim(x: Expr<String>) = FunctionExpr<String>(FunctionType.LTRIM, x)
	@JvmName("e_ltrim") fun Expr<String>.lTrim() = FunctionExpr<String>(FunctionType.LTRIM, this)

	fun replace(x: Expr<String>, oldSubstr: Expr<String>, newSubst: Expr<String>) =
		FunctionExpr<String>(FunctionType.REPLACE, x, oldSubstr, newSubst)

	fun replace(x: Expr<String>, oldSubstr: String, newSubst: String) =
		FunctionExpr<String>(FunctionType.REPLACE, x, StringLiteralExpr(oldSubstr), StringLiteralExpr(newSubst))

	@JvmName("e_replace") fun Expr<String>.replace(oldSubstr: Expr<String>, newSubst: Expr<String>) =
		FunctionExpr<String>(FunctionType.REPLACE, this, oldSubstr, newSubst)

	@JvmName("e_replace") fun Expr<String>.replace(oldSubstr: String, newSubst: String) =
		FunctionExpr<String>(FunctionType.REPLACE, this, StringLiteralExpr(oldSubstr), StringLiteralExpr(newSubst))

	fun toHex(x: Expr<String>) = FunctionExpr<String>(FunctionType.TO_HEX, x)
	@JvmName("e_toHex") fun Expr<String>.toHex() = FunctionExpr<String>(FunctionType.TO_HEX, this)

}


object AggFn {

	fun count() = FunctionExpr<Int>(FunctionType.AGG_COUNT_ALL)
	fun count(e: Expr<*>) = FunctionExpr<Int>(FunctionType.AGG_COUNT, e)
	@JvmName("e_count") fun Expr<*>.count() = FunctionExpr<Int>(FunctionType.AGG_COUNT, this)

	fun countDistinct() = FunctionExpr<Int>(FunctionType.AGG_COUNT_ALL_DISTINCT)
	fun countDistinct(e: Expr<*>) = FunctionExpr<Int>(FunctionType.AGG_COUNT_DISTINCT, e)
	@JvmName("e_countDistinct") fun Expr<*>.countDistinct() = FunctionExpr<Int>(FunctionType.AGG_COUNT_DISTINCT, this)

	fun <T> min(e: Expr<T>) = FunctionExpr<T>(FunctionType.AGG_MIN, e)
	fun <T> aggMin(e: Expr<T>) = FunctionExpr<T>(FunctionType.AGG_MIN, e)

	@JvmName("e_min") fun <T> Expr<T>.min() = FunctionExpr<T>(FunctionType.AGG_MIN, this)
	@JvmName("e_aggMin") fun <T> Expr<T>.aggMin() = FunctionExpr<T>(FunctionType.AGG_MIN, this)

	fun <T> max(e: Expr<T>) = FunctionExpr<T>(FunctionType.AGG_MAX, e)
	fun <T> aggMax(e: Expr<T>) = FunctionExpr<T>(FunctionType.AGG_MAX, e)

	@JvmName("e_max") fun <T> Expr<T>.max() = FunctionExpr<T>(FunctionType.AGG_MAX, this)
	@JvmName("e_aggMax") fun <T> Expr<T>.aggMax() = FunctionExpr<T>(FunctionType.AGG_MAX, this)

	@JvmName("agg_sum_s") fun sum(e: Expr<Short>) = FunctionExpr<Short>(FunctionType.AGG_SUM, e)
	@JvmName("agg_sum_i") fun sum(e: Expr<Int>) = FunctionExpr<Int>(FunctionType.AGG_SUM, e)
	@JvmName("agg_sum_l") fun sum(e: Expr<Long>) = FunctionExpr<Long>(FunctionType.AGG_SUM, e)
	@JvmName("agg_sum_f") fun sum(e: Expr<Float>) = FunctionExpr<Float>(FunctionType.AGG_SUM, e)
	@JvmName("agg_sum_d") fun sum(e: Expr<Double>) = FunctionExpr<Double>(FunctionType.AGG_SUM, e)

	@JvmName("e_agg_sum_s") fun Expr<Short>.sum() = FunctionExpr<Short>(FunctionType.AGG_SUM, this)
	@JvmName("e_agg_sum_i") fun Expr<Int>.sum() = FunctionExpr<Int>(FunctionType.AGG_SUM, this)
	@JvmName("e_agg_sum_l") fun Expr<Long>.sum() = FunctionExpr<Long>(FunctionType.AGG_SUM, this)
	@JvmName("e_agg_sum_f") fun Expr<Float>.sum() = FunctionExpr<Float>(FunctionType.AGG_SUM, this)
	@JvmName("e_agg_sum_d") fun Expr<Double>.sum() = FunctionExpr<Double>(FunctionType.AGG_SUM, this)

	@JvmName("agg_total_s") fun total(e: Expr<Short>) = FunctionExpr<Short>(FunctionType.AGG_TOTAL, e)
	@JvmName("agg_total_i") fun total(e: Expr<Int>) = FunctionExpr<Int>(FunctionType.AGG_TOTAL, e)
	@JvmName("agg_total_l") fun total(e: Expr<Long>) = FunctionExpr<Long>(FunctionType.AGG_TOTAL, e)
	@JvmName("agg_total_f") fun total(e: Expr<Float>) = FunctionExpr<Float>(FunctionType.AGG_TOTAL, e)
	@JvmName("agg_total_d") fun total(e: Expr<Double>) = FunctionExpr<Double>(FunctionType.AGG_TOTAL, e)

	@JvmName("e_agg_total_s") fun Expr<Short>.total() = FunctionExpr<Short>(FunctionType.AGG_TOTAL, this)
	@JvmName("e_agg_total_i") fun Expr<Int>.total() = FunctionExpr<Int>(FunctionType.AGG_TOTAL, this)
	@JvmName("e_agg_total_l") fun Expr<Long>.total() = FunctionExpr<Long>(FunctionType.AGG_TOTAL, this)
	@JvmName("e_agg_total_f") fun Expr<Float>.total() = FunctionExpr<Float>(FunctionType.AGG_TOTAL, this)
	@JvmName("e_agg_total_d") fun Expr<Double>.total() = FunctionExpr<Double>(FunctionType.AGG_TOTAL, this)


	fun concat(e: Expr<String>) = FunctionExpr<String>(FunctionType.AGG_CONCAT, e)
	fun groupConcat(e: Expr<String>) = FunctionExpr<String>(FunctionType.AGG_CONCAT, e)

	@JvmName("e_concat") fun Expr<String>.concat(separator: String) =
		FunctionExpr<String>(FunctionType.AGG_CONCAT, this, StringLiteralExpr(separator))

	@JvmName("e_groupConcat") fun Expr<String>.groupConcat(separator: String) =
		FunctionExpr<String>(FunctionType.AGG_CONCAT, this, StringLiteralExpr(separator))

}
