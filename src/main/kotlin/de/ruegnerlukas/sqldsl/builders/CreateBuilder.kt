package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.create.CreateTableStatement
import de.ruegnerlukas.sqldsl.schema.Table

interface CreateBuilderEndStep {
	fun build(): CreateTableStatement
}

class CreateBuilder {

	fun create(table: Table<*>) = PostTableCreateBuilder(table)

}

class PostTableCreateBuilder(private val table: Table<*>) : CreateBuilderEndStep {

	fun ifNotExists() = PostIfNotExistsBuilder(table, true)

	override fun build(): CreateTableStatement {
		return CreateTableStatement(table, false)
	}

}

class PostIfNotExistsBuilder(private val table: Table<*>, private val onlyIfNotExists: Boolean) : CreateBuilderEndStep {

	override fun build(): CreateTableStatement {
		return CreateTableStatement(table, onlyIfNotExists)
	}

}