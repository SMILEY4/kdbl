package de.ruegnerlukas.sqldsl.core.schema

enum class OnConflict {
    ROLLBACK,
    ABORT,
    FAIL,
    IGNORE,
    REPLACE
}