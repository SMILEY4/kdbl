package de.ruegnerlukas.sqldsl.schema

enum class OnUpdate {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}