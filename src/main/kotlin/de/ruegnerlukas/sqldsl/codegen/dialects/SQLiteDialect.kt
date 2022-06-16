package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expression.DataType
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionType
import de.ruegnerlukas.sqldsl.dsl.statements.Dir
import de.ruegnerlukas.sqldsl.dsl.statements.JoinOp

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

}