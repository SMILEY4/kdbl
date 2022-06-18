package de.ruegnerlukas.sqldsl.codegen.tokens

/**
 * A token that does not appear in any resulting string
 */
class NoOpToken : Token() {

    override fun buildString(): String {
		throw UnsupportedOperationException("Cant build string of NoOp-Token")
	}

	override fun buildExtended(placeholders: MutableList<String>) = buildString()
}
