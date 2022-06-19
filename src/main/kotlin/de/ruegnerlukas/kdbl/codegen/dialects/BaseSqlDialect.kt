package de.ruegnerlukas.kdbl.codegen.dialects

import de.ruegnerlukas.kdbl.codegen.tokens.ListToken
import de.ruegnerlukas.kdbl.codegen.tokens.NamedGroupToken
import de.ruegnerlukas.kdbl.codegen.tokens.StringToken
import de.ruegnerlukas.kdbl.codegen.tokens.Token
import de.ruegnerlukas.kdbl.dsl.expression.Expr
import de.ruegnerlukas.kdbl.dsl.expression.FunctionType
import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime

/**
 * Base dialect with common code-generation
 */
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
			FunctionType.AGG_COUNT -> NamedGroupToken("COUNT", exprBuilder(args[0]))
			FunctionType.AGG_COUNT_DISTINCT -> NamedGroupToken("COUNT", ListToken().add("DISTINCT").add(exprBuilder(args[0])))
			else -> null
		}
	}
}