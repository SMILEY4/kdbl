package de.ruegnerlukas.sqldsl.utils

class SqlDate(
	val year: Int,
	val month: Int,
	val day: Int
) {
	fun strYear() = year.toString().padStart(4, '0')
	fun strMonth() = month.toString().padStart(2, '0')
	fun strDay() = day.toString().padStart(2, '0')
}
