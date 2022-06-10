package de.ruegnerlukas.sqldsl.dsl.grammar.column

enum class OnDelete {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}