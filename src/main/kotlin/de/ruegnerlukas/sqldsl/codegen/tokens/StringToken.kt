package de.ruegnerlukas.sqldsl.codegen.tokens

class StringToken(private val value: String) : Token() {
    override fun buildString() = value
}