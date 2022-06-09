package de.ruegnerlukas.sqldsl.grammar.create

import de.ruegnerlukas.sqldsl.schema.Table

class CreateTableStatement(val table: Table<*>, val onlyIfNotExists: Boolean)