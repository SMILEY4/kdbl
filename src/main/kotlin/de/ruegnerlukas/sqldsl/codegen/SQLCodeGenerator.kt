package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement

interface SQLCodeGenerator {
	fun create(create: CreateTableStatement): Token
	fun insert(insert: InsertStatement): Token
	fun update(update: UpdateStatement): Token
	fun delete(delete: DeleteStatement): Token
	fun query(query: QueryStatement<*>): Token
}