package de.ruegnerlukas.sqldsl.core.generators

import de.ruegnerlukas.sqldsl.core.actions.insert.InsertStatement

interface InsertIntoGenerator {
    fun build(stmt: InsertStatement): String
}