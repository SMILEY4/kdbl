package de.ruegnerlukas.kdbl.codegen.dialects

import de.ruegnerlukas.kdbl.codegen.tokens.CsvListToken
import de.ruegnerlukas.kdbl.codegen.tokens.ListToken
import de.ruegnerlukas.kdbl.codegen.tokens.StringToken
import de.ruegnerlukas.kdbl.codegen.tokens.Token
import de.ruegnerlukas.kdbl.dsl.expression.DataType
import de.ruegnerlukas.kdbl.dsl.expression.FunctionType
import de.ruegnerlukas.kdbl.dsl.expression.JoinOp
import de.ruegnerlukas.kdbl.dsl.statements.Dir

/**
 * Code generation for SQLite
 */
class SQLiteDialect : BaseSqlDialect() {

	override fun dataType(type: DataType): String {
		return when (type) {
			DataType.BOOL -> "BOOLEAN"
			DataType.SMALLINT -> "INTEGER"
			DataType.INT -> "INTEGER"
			DataType.BIGINT -> "INTEGER"
			DataType.FLOAT -> "REAL"
			DataType.DOUBLE -> "REAL"
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
		if (isPrimaryKey && dataType == DataType.SMALLINT || dataType == DataType.INT || dataType == DataType.BIGINT) {
			fnInsertConstraint("AUTOINCREMENT")
		} else {
			fnForbidAutoIncrement()
		}
	}

	override fun joinOperation(op: JoinOp) = when (op) {
		JoinOp.INNER -> "INNER JOIN"
		JoinOp.LEFT -> "LEFT JOIN"
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
		FunctionType.RANDOM -> "RANDOM"
		FunctionType.ACOS -> "ACOS"
		FunctionType.ASIN -> "ASIN"
		FunctionType.ATAN -> "ATAN"
		FunctionType.ATAN2 -> "ATAN2"
		FunctionType.SIN -> "SIN"
		FunctionType.COS -> "COS"
		FunctionType.TAN -> "TAN"
		FunctionType.SINH -> "SINH"
		FunctionType.COSH -> "COSH"
		FunctionType.TANH -> "TANH"
		FunctionType.ASINH -> "ASINH"
		FunctionType.ACOSH -> "ACOSH"
		FunctionType.ATANH -> "ATANH"
		FunctionType.CONCAT -> null
		FunctionType.LENGTH -> "LENGTH"
		FunctionType.LOWER -> "LOWER"
		FunctionType.UPPER -> "UPPER"
		FunctionType.SUBSTRING -> "SUBSTRING"
		FunctionType.TRIM -> "TRIM"
		FunctionType.RTRIM -> "RTRIM"
		FunctionType.LTRIM -> "LTRIM"
		FunctionType.REPLACE -> "REPLACE"
		FunctionType.TO_HEX -> "HEX"
		FunctionType.AGG_MIN -> "MIN"
		FunctionType.AGG_MAX -> "MAX"
		FunctionType.AGG_SUM -> "SUM"
		FunctionType.AGG_TOTAL -> "TOTAL"
		FunctionType.AGG_CONCAT -> "GROUP_CONCAT"
		else -> null
	}

	override fun upsertClause(columns: List<String>): Token {
		return ListToken()
			.add("ON CONFLICT DO UPDATE SET")
			.add(
				CsvListToken(
					columns.map { StringToken("$it = EXCLUDED.$it") }
				)
			)
	}

}