package de.ruegnerlukas.sqldsl.codegen.tokens

class NoOpToken : Token() {
    override fun buildString(): String {
        throw UnsupportedOperationException("Cant build string of NoOp-Token")
    }
}
