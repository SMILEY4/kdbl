package de.ruegnerlukas.sqldsl.dsl.grammar.column

enum class OnUpdate {
    NO_ACTION,
    RESTRICT,
    SET_NULL,
    SET_DEFAULT,
    CASCADE,
}