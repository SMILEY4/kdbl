package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement

class CreateTableBuilder {

	fun create(table: Table) = CreateTableStatement(table, false)

	fun createIfNotExists(table: Table) = CreateTableStatement(table, true)

}