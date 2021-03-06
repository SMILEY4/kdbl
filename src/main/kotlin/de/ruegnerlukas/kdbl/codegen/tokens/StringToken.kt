package de.ruegnerlukas.kdbl.codegen.tokens

/**
 * A token consisting of a single string
 */
class StringToken(private val value: String) : Token() {
	override fun buildString() = value
	override fun buildExtended(placeholders: MutableList<String>) = buildString()
}