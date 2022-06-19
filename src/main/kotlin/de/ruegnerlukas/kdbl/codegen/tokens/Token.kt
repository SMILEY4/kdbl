package de.ruegnerlukas.kdbl.codegen.tokens

/**
 * A generic token
 */
abstract class Token {

	/**
	 * Build a string from this token / this tree of tokens
	 */
	abstract fun buildString(): String


	/**
	 * Build a string from this token / this tree of tokens. All visited [PlaceholderToken] add their name to the given list.
	 */
	abstract fun buildExtended(placeholders: MutableList<String>): String
}











