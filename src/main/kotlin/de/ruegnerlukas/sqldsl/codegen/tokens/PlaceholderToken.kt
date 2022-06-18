package de.ruegnerlukas.sqldsl.codegen.tokens

/**
 * A token for named placeholder values. Appears as a "?" in the resulting string.
 */
class PlaceholderToken(private val name: String) : Token() {

	override fun buildString() = "?"

	override fun buildExtended(placeholders: MutableList<String>): String {
		placeholders.add(name)
		return buildString()
	}

}