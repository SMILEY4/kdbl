package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expression.DataType
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionType
import de.ruegnerlukas.sqldsl.dsl.statements.Dir
import de.ruegnerlukas.sqldsl.dsl.statements.JoinOp

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

	override fun functionName(t: FunctionType): String? {
		return when (t) {
			FunctionType.ABS -> "ABS"
			FunctionType.HEX -> "HEX"
			FunctionType.LENGTH -> "LENGTH"
			FunctionType.LOWER -> "LOWER"
			FunctionType.UPPER -> "UPPER"
			FunctionType.LTRIM -> "LTRIM"
			FunctionType.RTRIM -> "RTRIM"
			FunctionType.TRIM -> "TRIM"
			FunctionType.MAX -> "MAX"
			FunctionType.MIN -> "MIN"
			FunctionType.RANDOM -> "RANDOM"
			FunctionType.REPLACE -> "REPLACE"
			FunctionType.ROUND -> "ROUND"
			FunctionType.SIGN -> "SIGN"
			FunctionType.SUBSTRING -> "SUBSTRING"
			FunctionType.AGG_MIN -> "MIN"
			FunctionType.AGG_MAX -> "MAX"
			FunctionType.AGG_SUM -> "SUM"
			else -> null
		}
	}

}