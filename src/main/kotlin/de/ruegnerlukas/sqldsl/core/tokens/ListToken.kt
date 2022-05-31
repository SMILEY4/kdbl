package de.ruegnerlukas.sqldsl.core.tokens

class ListToken(tokens: List<Token> = listOf(), private val newLines: Boolean = false) : Token() {

	private val tokens = tokens.toMutableList()

	fun add(token: Token): ListToken {
		tokens.add(token)
		return this
	}

	fun addIf(condition: Boolean, block: () -> Token): ListToken {
		if (condition) {
			tokens.add(block())
		}
		return this
	}

	fun add(token: String): ListToken {
		tokens.add(StringToken(token))
		return this
	}

	fun addIf(token: String, condition: () -> Boolean): ListToken {
		if (condition()) {
			tokens.add(StringToken(token))
		}
		return this
	}

	fun then(block: ListToken.() -> Unit): ListToken {
		block()
		return this
	}


	override fun buildString(): String {
		return tokens
			.filter { it !is NoOpToken }
			.map { it.buildString() }
			.joinToString(if (newLines) "\n" else " ")
	}
}