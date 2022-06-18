package de.ruegnerlukas.sqldsl.codegen.tokens

class PlaceholderToken(private val name: String) : Token() {
	override fun buildString() = "?"

	override fun buildExtended(placeholders: MutableList<String>): String {
		placeholders.add(name)
		return buildString()
	}

}