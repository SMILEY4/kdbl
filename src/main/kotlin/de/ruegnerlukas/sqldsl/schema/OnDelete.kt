package de.ruegnerlukas.sqldsl.schema

enum class OnDelete {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}