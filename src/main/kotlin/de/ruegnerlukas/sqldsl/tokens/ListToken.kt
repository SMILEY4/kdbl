package de.ruegnerlukas.sqldsl.tokens

class ListToken(tokens: List<Token> = listOf()) : Token() {

	private val tokens = tokens.toMutableList()

	fun add(token: Token): ListToken {
		tokens.add(token)
		return this
	}

	fun add(token: String): ListToken {
		tokens.add(StringToken(token))
		return this
	}

	fun addAll(tokens: Collection<Token>): ListToken {
		this.tokens.addAll(tokens)
		return this
	}

	fun addIf(condition: Boolean, token: () -> Token): ListToken {
		if (condition) {
			tokens.add(token())
		}
		return this
	}

	fun addIf(condition: Boolean, token: String): ListToken {
		if (condition) {
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
			.joinToString(" ")
	}
}