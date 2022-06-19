package de.ruegnerlukas.kdbl.codegen.tokens

/**
 * A string followed by the token surrounded by braces "NAME(token)"
 */
class NamedGroupToken(private val name: String, private val token: Token) : Token() {

	override fun buildString(): String {
		return "$name(${token.buildString()})"
	}

	override fun buildExtended(placeholders: MutableList<String>) = buildString()
}