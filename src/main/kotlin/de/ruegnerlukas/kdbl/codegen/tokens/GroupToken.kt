package de.ruegnerlukas.kdbl.codegen.tokens

/**
 * A token surrounded by braces "(token)"
 */
class GroupToken(private val token: Token) : Token() {

	override fun buildString(): String {
		return when(token) {
			is StringToken -> token.buildString()
			else -> "(${token.buildString()})"
		}
	}

	override fun buildExtended(placeholders: MutableList<String>): String {
		return when(token) {
			is StringToken -> token.buildExtended(placeholders)
			is PlaceholderToken -> token.buildExtended(placeholders)
			else -> "(${token.buildExtended(placeholders)})"
		}
	}
}