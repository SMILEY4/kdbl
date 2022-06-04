package de.ruegnerlukas.sqldsl2.schema

enum class OnConflict {
    ROLLBACK,
    ABORT,
    FAIL,
    IGNORE,
    REPLACE
}