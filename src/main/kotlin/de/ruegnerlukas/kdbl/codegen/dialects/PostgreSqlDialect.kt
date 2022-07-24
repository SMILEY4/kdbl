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
 * Code generation for PostgreSQL
 */
class PostgreSqlDialect : BaseSqlDialect() {

	override fun dataType(type: DataType): String {
		return when (type) {
			DataType.BOOL -> "BOOLEAN"
			DataType.SMALLINT -> "SMALLINT"
			DataType.INT -> "INT"
			DataType.BIGINT -> "BIGINT"
			DataType.FLOAT -> "REAL"
			DataType.DOUBLE -> "DOUBLE PRECISION"
			DataType.TEXT -> "TEXT"
			DataType.DATE -> "DATE"
			DataType.TIME -> "TIME"
			DataType.TIMESTAMP -> "TIMESTAMP"
		}
	}

	override fun orderField(field: Token, strDirection: String, dir: Dir): Token {
		return when(dir) {
			Dir.ASC -> ListToken()
				.add(field)
				.add(strDirection)
				.add("NULLS FIRST")
			Dir.DESC -> ListToken()
				.add(field)
				.add(strDirection)
				.add("NULLS LAST")
		}
	}

	override fun autoIncrementColumn(
		dataType: DataType,
		isPrimaryKey: Boolean,
		fnReplaceColumnType: (type: String) -> Unit,
		fnInsertConstraint: (constraint: String) -> Unit,
		fnForbidAutoIncrement: () -> Unit
	) {
		when(dataType) {
			DataType.SMALLINT -> fnReplaceColumnType("SMALLSERIAL")
			DataType.INT -> fnReplaceColumnType("SERIAL")
			DataType.BIGINT -> fnReplaceColumnType("BIGSERIAL")
			else -> fnForbidAutoIncrement()
		}
	}

	override fun joinOperation(op: JoinOp) = when (op) {
		JoinOp.INNER -> "INNER JOIN"
		JoinOp.LEFT -> "LEFT JOIN"
		JoinOp.RIGHT -> "RIGHT JOIN"
		JoinOp.FULL -> "FULL JOIN"
	}

	override fun functionName(f: FunctionType) = when(f) {
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
		FunctionType.CONCAT -> "CONCAT"
		FunctionType.LENGTH -> "LENGTH"
		FunctionType.LOWER -> "LOWER"
		FunctionType.UPPER -> "UPPER"
		FunctionType.SUBSTRING -> "SUBSTRING"
		FunctionType.TRIM -> "TRIM"
		FunctionType.RTRIM -> "RTRIM"
		FunctionType.LTRIM -> "LTRIM"
		FunctionType.REPLACE -> "REPLACE"
		FunctionType.TO_HEX -> "TO_HEX"
		FunctionType.AGG_MIN -> "AGG_MIN"
		FunctionType.AGG_MAX -> "AGG_MAX"
		FunctionType.AGG_SUM -> null
		FunctionType.AGG_TOTAL -> "SUM"
		FunctionType.AGG_CONCAT -> "STRING_AGG"
		else -> null
	}

	override fun upsertClause(columns: List<String>): Token {
		return ListToken()
			.add("ON CONFLICT TO UPDATE SET")
			.add(
				CsvListToken(
					columns.map { StringToken("$it = EXCLUDED.$it") }
				)
			)
	}

}