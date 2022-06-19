package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.NamedGroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expression.DataType
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionType
import de.ruegnerlukas.sqldsl.dsl.expression.JoinOp
import de.ruegnerlukas.sqldsl.dsl.statements.Dir

/**
 * Code generation for MySql
 */
class MySqlDialect : BaseSqlDialect() {

	override fun dataType(type: DataType): String {
		return when (type) {
			DataType.BOOL -> "BOOLEAN"
			DataType.SMALLINT -> "INTEGER"
			DataType.INT -> "INT"
			DataType.BIGINT -> "BIGINT"
			DataType.FLOAT -> "FLOAT"
			DataType.DOUBLE -> "DOUBLE"
			DataType.TEXT -> "TEXT"
			DataType.DATE -> "DATE"
			DataType.TIME -> "TIME"
			DataType.TIMESTAMP -> "TIMESTAMP"
		}
	}

	override fun orderField(field: Token, strDirection: String, dir: Dir) = ListToken().add(field).add(strDirection)

	override fun autoIncrementColumn(
		dataType: DataType,
		isPrimaryKey: Boolean,
		fnReplaceColumnType: (type: String) -> Unit,
		fnInsertConstraint: (constraint: String) -> Unit,
		fnForbidAutoIncrement: () -> Unit
	) {
		if (dataType == DataType.SMALLINT || dataType == DataType.INT || dataType == DataType.BIGINT) {
			fnInsertConstraint("AUTO_INCREMENT")
		} else {
			fnForbidAutoIncrement()
		}
	}

	override fun joinOperation(op: JoinOp) = when (op) {
		JoinOp.INNER -> "INNER JOIN"
		JoinOp.LEFT -> "LEFT JOIN"
		JoinOp.RIGHT -> "RIGHT JOIN"
		else -> null
	}

	override fun functionName(f: FunctionType) = when (f) {
		FunctionType.ABS -> "ABS"
		FunctionType.CEIL -> "CEIL"
		FunctionType.FLOOR -> "FLOOR"
		FunctionType.ROUND -> "ROUND"
		FunctionType.RAD_TO_DEG -> "DEGREES"
		FunctionType.DEG_TO_RAD -> "RADIANS"
		FunctionType.EXPONENTIAL -> "EXP"
		FunctionType.LN -> "LN"
		FunctionType.LOG -> "LOG"
		FunctionType.MOD -> "MOD"
		FunctionType.PI -> "PI"
		FunctionType.POWER -> "POWER"
		FunctionType.SIGN -> "SIGN"
		FunctionType.SQRT -> "SQRT"
		FunctionType.MAX -> "MAX"
		FunctionType.MIN -> "MIN"
		FunctionType.RANDOM -> "RAND"
		FunctionType.ACOS -> "ACOS"
		FunctionType.ASIN -> "ASIN"
		FunctionType.ATAN -> "ATAN"
		FunctionType.ATAN2 -> "ATAN2"
		FunctionType.SIN -> "SIN"
		FunctionType.COS -> "COS"
		FunctionType.TAN -> "TAN"
		FunctionType.SINH -> null
		FunctionType.COSH -> null
		FunctionType.TANH -> null
		FunctionType.ASINH -> null
		FunctionType.ACOSH -> null
		FunctionType.ATANH -> null
		FunctionType.CONCAT -> "CONCAT"
		FunctionType.LENGTH -> "CHARACTER_LENGTH"
		FunctionType.LOWER -> "LOWER"
		FunctionType.UPPER -> "UPPER"
		FunctionType.SUBSTRING -> "SUBSTRING"
		FunctionType.TRIM -> "TRIM"
		FunctionType.RTRIM -> "RTRIM"
		FunctionType.LTRIM -> "LTRIM"
		FunctionType.REPLACE -> "REPLACE"
		FunctionType.TO_HEX -> null
		FunctionType.AGG_MIN -> "MIN"
		FunctionType.AGG_MAX -> "MAX"
		FunctionType.AGG_SUM -> "SUM"
		FunctionType.AGG_TOTAL -> "SUM"
		FunctionType.AGG_CONCAT -> null
		else -> null
	}

	override fun function(type: FunctionType, args: List<Expr<*>>, exprBuilder: (e: Expr<*>) -> Token) = when (type) {
		FunctionType.AGG_CONCAT -> NamedGroupToken(
			"GROUP_CONCAT",
			CsvListToken()
				.addAll(args.subList(1, args.size).map(exprBuilder))
				.add("SEPARATOR")
				.add(exprBuilder(args[0]))
		)
		else -> super.function(type, args, exprBuilder)
	}
}