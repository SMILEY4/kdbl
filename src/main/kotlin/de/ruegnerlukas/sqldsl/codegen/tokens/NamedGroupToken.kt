package de.ruegnerlukas.sqldsl.codegen.tokens

class NamedGroupToken(private val name: String, private val token: Token) : Token() {
	override fun buildString(): String {
		return "$name(${token.buildString()})"
	}
}