package de.ruegnerlukas.sqldsl.core.tokens

class GroupToken(private val token: Token, private val contentOnOwnLine: Boolean = false, private val identContent: Boolean = false) :
    Token() {
    override fun buildString(): String {
        if (contentOnOwnLine) {
            if (identContent) {
                val lines = token.buildString().split("\n").map { "    $it" }.joinToString("\n")
                return "(\n$lines\n)"
            } else {
                return "(\n${token.buildString()}\n)"
            }
        } else {
            return "(${token.buildString()})"
        }
    }
}