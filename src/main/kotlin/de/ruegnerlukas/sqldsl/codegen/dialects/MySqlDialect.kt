package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.dsl.expr.DataType

class MySqlDialect: SQLDialect {

	override fun dataType(type: DataType): String {
		return when(type) {
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
			DataType.BLOB -> "BLOB"
		}
	}

}