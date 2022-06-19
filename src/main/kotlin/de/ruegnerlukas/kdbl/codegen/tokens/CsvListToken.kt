package de.ruegnerlukas.kdbl.codegen.tokens

/**
 * A list of tokens separated by a comma "token1,token2,..."
 */
class CsvListToken(tokens: List<Token> = listOf()) : Token() {

	private val tokens = tokens.toMutableList()


	/**
	 * Add the given token to the list
	 */
	fun add(token: Token): CsvListToken {
		tokens.add(token)
		return this
	}

	/**
	 * Add the given string as a token to the list
	 */
	fun add(token: String): CsvListToken {
		tokens.add(StringToken(token))
		return this
	}

	/**
	 * Add all given tokens to the list
	 */
	fun addAll(tokens: Collection<Token>): CsvListToken {
		this.tokens.addAll(tokens)
		return this
	}

	/**
	 * Add the given token to the list only if the given condition matches
	 */
	fun addIf(condition: Boolean, token: () -> Token): CsvListToken {
		if (condition) {
			tokens.add(token())
		}
		return this
	}

	/**
	 * Add the given string as a token to the list only if the given condition matches
	 */
	fun addIf(condition: Boolean, token: String): CsvListToken {
		if (condition) {
			tokens.add(StringToken(token))
		}
		return this
	}

	override fun buildString(): String {
		return tokens
			.filter { it !is NoOpToken }
			.map { it.buildString() }
			.joinToString(", ")
	}

	override fun buildExtended(placeholders: MutableList<String>) = buildString()

}