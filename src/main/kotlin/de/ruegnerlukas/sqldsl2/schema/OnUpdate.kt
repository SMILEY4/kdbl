package de.ruegnerlukas.sqldsl2.schema

enum class OnUpdate {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}