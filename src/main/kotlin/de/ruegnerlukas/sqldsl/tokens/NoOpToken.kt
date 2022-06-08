package de.ruegnerlukas.sqldsl.tokens

class NoOpToken : Token() {
    override fun buildString(): String {
        throw UnsupportedOperationException("Cant build string of NoOp-Token")
    }
}
