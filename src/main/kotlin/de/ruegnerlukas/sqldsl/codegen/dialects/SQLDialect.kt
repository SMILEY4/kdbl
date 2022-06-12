package de.ruegnerlukas.sqldsl.codegen.dialects

import de.ruegnerlukas.sqldsl.dsl.expr.DataType

interface SQLDialect {

	fun dataType(type: DataType): String

}