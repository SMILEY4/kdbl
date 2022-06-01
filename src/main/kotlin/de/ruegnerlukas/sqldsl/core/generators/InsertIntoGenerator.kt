package de.ruegnerlukas.sqldsl.core.generators

import de.ruegnerlukas.sqldsl.core.builders.insert.InsertStatement

interface InsertIntoGenerator {
    fun build(stmt: InsertStatement): String
}