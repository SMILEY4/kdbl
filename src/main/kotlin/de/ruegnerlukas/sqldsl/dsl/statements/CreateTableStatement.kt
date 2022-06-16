package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Table

class CreateTableStatement(val table: Table, val onlyIfNotExists: Boolean)
