package de.ruegnerlukas.sqldsl.core.tokens

class StringToken(private val value: String) : Token() {
    override fun buildString() = value
}