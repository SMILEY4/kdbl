package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.schema.Table

interface CreateBuilderEndStep {
	fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement
}

class CreateBuilder {

	fun create(table: Table<*>) = PostTableCreateBuilder(table)

}

class PostTableCreateBuilder(private val table: Table<*>) : CreateBuilderEndStep {

	fun ifNotExists() = PostIfNotExistsBuilder(table, true)

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement {
		return de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement(table, false)
	}

}

class PostIfNotExistsBuilder(private val table: Table<*>, private val onlyIfNotExists: Boolean) : CreateBuilderEndStep {

	override fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement {
		return de.ruegnerlukas.sqldsl.dsl.grammar.create.CreateTableStatement(table, onlyIfNotExists)
	}

}