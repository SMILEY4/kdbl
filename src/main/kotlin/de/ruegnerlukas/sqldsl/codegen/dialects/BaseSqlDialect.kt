package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionType
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

	override fun function(type: FunctionType, args: List<Expr<*>>, exprBuilder: (e: Expr<*>) -> Token): Token? {
		return when(type) {
			FunctionType.AGG_COUNT_ALL -> StringToken("COUNT(*)")
			FunctionType.AGG_COUNT_ALL_DISTINCT -> StringToken("COUNT( DISTINCT * )")
			FunctionType.AGG_COUNT -> StringToken("COUNT(${exprBuilder(args[0])})")
			FunctionType.AGG_COUNT_DISTINCT -> StringToken("COUNT( DISTINCT ${exprBuilder(args[0])} )")
			else -> null
		}
	}
}