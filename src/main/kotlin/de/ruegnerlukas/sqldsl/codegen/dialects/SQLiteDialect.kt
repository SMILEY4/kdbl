package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.dsl.expr.DataType

class SQLiteDialect: SQLDialect {

	// https://www.techonthenet.com/sqlite/datatypes.php
	override fun dataType(type: DataType): String {
		return when(type) {
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
			DataType.BLOB -> "BLOB"
		}
	}

}