package de.ruegnerlukas.kdbl.db

import de.ruegnerlukas.kdbl.codegen.SQLCodeGenerator
import java.sql.Connection

/**
 * A database using the single given connection for all operations
 * @param connection the connection for all operations
 * @param codeGen the generator converting statements into sql-strings
 * @param cache the reusable sql-strings and placeholders, initializing the cache of this database-instance
 */
class SingleConnectionDatabase(
	private val connection: Connection,
	codeGen: SQLCodeGenerator,
	cache: SqlCache = SqlCache()
) : Database(codeGen, cache) {

	override suspend fun <R> getConnection(block: suspend (connection: Connection) -> R): R {
		return block(connection)
	}

}