package de.ruegnerlukas.sqldsl.codegen.tokens


/**
 * A list of tokens separated by an empty space "token1 token2 token3  ...
 */
class ListToken(tokens: List<Token> = listOf()) : Token() {

	private val tokens = tokens.toMutableList()


	/**
	 * Add the given token to the list
	 */
	fun add(token: Token): ListToken {
		tokens.add(token)
		return this
	}


	/**
	 * Add the given string as a token to the list
	 */
	fun add(token: String): ListToken {
		tokens.add(StringToken(token))
		return this
	}


	/**
	 * Add all given tokens to the list
	 */
	fun addAll(tokens: Collection<Token>): ListToken {
		this.tokens.addAll(tokens)
		return this
	}


	/**
	 * Add the given token to the list only if the given condition matches
	 */
	fun addIf(condition: Boolean, token: () -> Token): ListToken {
		if (condition) {
			tokens.add(token())
		}
		return this
	}


	/**
	 * Add the given string as a token to the list only if the given condition matches
	 */
	fun addIf(condition: Boolean, token: String): ListToken {
		if (condition) {
			tokens.add(StringToken(token))
		}
		return this
	}


	/**
	 * perform the given block with "this" list-token
	 */
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

	override fun buildExtended(placeholders: MutableList<String>) = buildString()
}