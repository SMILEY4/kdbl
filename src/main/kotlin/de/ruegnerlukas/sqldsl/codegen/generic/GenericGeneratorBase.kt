package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.tokens.Token

abstract class GenericGeneratorBase<T> {

	abstract fun buildToken(e: T): Token

	fun buildString(e: T): String {
		return buildToken(e).buildString()
	}

	fun throwUnknownType(e: Any): Token {
		throw Exception("Unknown type: {e}")
	}

}