package de.ruegnerlukas.kdbl.utils

class SqlTime(
	val hour: Int,
	val minute: Int,
	val second: Int
) {
	fun strHour() = hour.toString().padStart(2, '0')
	fun strMinute() = minute.toString().padStart(2, '0')
	fun strSecond() = second.toString().padStart(2, '0')
}