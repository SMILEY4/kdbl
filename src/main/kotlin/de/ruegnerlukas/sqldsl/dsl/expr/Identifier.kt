package de.ruegnerlukas.sqldsl.dsl

interface Identifier {
	fun getRefName(): String
}

class DirectIdentifier(private val name: String) : Identifier {
	override fun getRefName() = name
}

class QualifiedIdentifier(private val qualifier: String, private val name: String) : Identifier {
	override fun getRefName() = "$qualifier.$name"
}