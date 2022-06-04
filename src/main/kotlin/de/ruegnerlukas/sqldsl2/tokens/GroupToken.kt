package de.ruegnerlukas.sqldsl2.tokens

class GroupToken(private val token: Token) : Token() {
	override fun buildString(): String {
		return "(${token.buildString()})"
	}
}