package de.ruegnerlukas.sqldsl.tokens

class StringToken(private val value: String) : Token() {
    override fun buildString() = value
}