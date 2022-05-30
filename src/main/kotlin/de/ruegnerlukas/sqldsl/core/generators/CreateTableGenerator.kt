package de.ruegnerlukas.sqldsl.core.generators

import de.ruegnerlukas.sqldsl.core.schema.Table

interface CreateTableGenerator {
    fun build(table: Table): String
}