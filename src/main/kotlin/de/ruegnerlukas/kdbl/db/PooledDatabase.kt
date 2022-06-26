package de.ruegnerlukas.kdbl.db

import de.ruegnerlukas.kdbl.codegen.SQLCodeGenerator
import java.sql.Connection

/**
 * A database using the given [ConnectionPool]
 * @param connectionPool the pool of connections
 * @param codeGen the generator converting statements into sql-strings
 */
class PooledDatabase(private val connectionPool: ConnectionPool, codeGen: SQLCodeGenerator) : Database(codeGen) {

	override suspend fun <R> getConnection(block: suspend (connection: Connection) -> R): R {
		return connectionPool.getConnection().use { block(it) }
	}

}