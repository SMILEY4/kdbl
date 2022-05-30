package de.ruegnerlukas.sqldsl.core.tokens

class CsvListToken(tokens: List<Token> = listOf(), private val oneEntryPerLine: Boolean = false) : Token() {

    private val tokens = tokens.toMutableList()

    fun add(token: Token): CsvListToken {
        tokens.add(token)
        return this
    }

    fun addIf(token: Token, condition: () -> Boolean): CsvListToken {
        if (condition()) {
            tokens.add(token)
        }
        return this
    }

    fun add(token: String): CsvListToken {
        tokens.add(StringToken(token))
        return this
    }

    fun addIf(token: String, condition: () -> Boolean): CsvListToken {
        if (condition()) {
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
            .joinToString(if(oneEntryPerLine) ",\n" else ", ")
    }
}