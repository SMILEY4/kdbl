package de.ruegnerlukas.sqldsl2.schema

enum class OnDelete {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}