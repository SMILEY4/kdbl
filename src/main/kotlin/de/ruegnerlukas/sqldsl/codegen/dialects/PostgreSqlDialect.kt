package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.dsl.expr.DataType

class PostgreSqlDialect: SQLDialect {

	override fun dataType(type: DataType): String {
		return when(type) {
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
			DataType.BLOB -> "BLOB"
		}
	}

}