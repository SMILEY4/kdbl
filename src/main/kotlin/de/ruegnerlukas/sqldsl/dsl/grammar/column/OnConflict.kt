package de.ruegnerlukas.sqldsl.dsl.grammar.column

enum class OnConflict {
    ROLLBACK,
    ABORT,
    FAIL,
    IGNORE,
    REPLACE
}