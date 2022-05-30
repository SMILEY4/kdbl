package de.ruegnerlukas.sqldsl.core.tokens

class ListToken(tokens: List<Token> = listOf()) : Token() {

    private val tokens = tokens.toMutableList()

    fun add(token: Token): ListToken {
        tokens.add(token)
        return this
    }

    fun addIf(token: Token, condition: () -> Boolean): ListToken {
        if (condition()) {
            tokens.add(token)
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
            .joinToString(" ")
    }
}