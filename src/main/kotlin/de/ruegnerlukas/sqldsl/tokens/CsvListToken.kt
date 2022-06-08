package de.ruegnerlukas.sqldsl.tokens

class CsvListToken(tokens: List<Token> = listOf()) : Token() {

	private val tokens = tokens.toMutableList()

	fun add(token: Token): CsvListToken {
		tokens.add(token)
		return this
	}

	fun add(token: String): CsvListToken {
		tokens.add(StringToken(token))
		return this
	}


	fun addAll(tokens: Collection<Token>): CsvListToken {
		this.tokens.addAll(tokens)
		return this
	}

	fun addIf(condition: Boolean, token: () -> Token): CsvListToken {
		if (condition) {
			tokens.add(token())
		}
		return this
	}

	fun addIf(condition: Boolean, token: String): CsvListToken {
		if (condition) {
			tokens.add(StringToken(token))
		}
		return this
	}

	fun then(block: CsvListToken.() -> Unit): CsvListToken {
		block()
		return this
	}

	override fun buildString(): String {
		return tokens
			.filter { it !is NoOpToken }
			.map { it.buildString() }
			.joinToString(", ")
	}
}