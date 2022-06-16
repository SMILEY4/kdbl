package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

abstract class BaseSqlDialect : SQLDialect {

	override fun booleanLiteral(value: Boolean) = when (value) {
		true -> "TRUE"
		false -> "FALSE"
	}

	override fun stringLiteral(value: String) = "'$value'"

	override fun dateLiteral(value: SqlDate) = "'${value.strYear()}-${value.strMonth()}-${value.strDay()}'"

	override fun timeLiteral(value: SqlTime) = "'${value.strHour()}:${value.strMinute()}:${value.strSecond()}'"

}