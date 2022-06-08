package de.ruegnerlukas.sqldsl.schema

enum class OnConflict {
    ROLLBACK,
    ABORT,
    FAIL,
    IGNORE,
    REPLACE
}