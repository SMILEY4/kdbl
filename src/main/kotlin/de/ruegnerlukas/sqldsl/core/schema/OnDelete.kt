package de.ruegnerlukas.sqldsl.core.schema

enum class OnDelete {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}