package de.ruegnerlukas.kdbl.db

import de.ruegnerlukas.kdbl.codegen.SQLCodeGenerator

/**
 * A database using the given [ConnectionPool]
 * @param connectionPool the pool of connections
 * @param codeGen the generator converting statements into sql-strings
 */
class PooledDatabase(private val connectionPool: ConnectionPool, codeGen: SQLCodeGenerator) : Database(codeGen) {

	override fun getConnection() = connectionPool.getConnection()

}