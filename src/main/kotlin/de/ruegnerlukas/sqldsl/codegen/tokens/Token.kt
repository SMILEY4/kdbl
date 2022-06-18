package de.ruegnerlukas.sqldsl.codegen.tokens

abstract class Token {
    abstract fun buildString(): String
    abstract fun buildExtended(placeholders: MutableList<String>): String
}











