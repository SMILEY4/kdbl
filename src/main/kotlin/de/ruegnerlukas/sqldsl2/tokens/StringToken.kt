package de.ruegnerlukas.sqldsl2.tokens

class StringToken(private val value: String) : Token() {
    override fun buildString() = value
}