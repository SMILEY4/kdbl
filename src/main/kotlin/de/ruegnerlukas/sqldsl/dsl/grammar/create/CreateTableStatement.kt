package de.ruegnerlukas.sqldsl.dsl.grammar.create

import de.ruegnerlukas.sqldsl.dsl.schema.Table

class CreateTableStatement(val table: Table<*>, val onlyIfNotExists: Boolean)