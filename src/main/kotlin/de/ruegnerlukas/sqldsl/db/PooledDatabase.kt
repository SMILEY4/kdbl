package de.ruegnerlukas.sqldsl.db

import de.ruegnerlukas.sqldsl.codegen.SQLCodeGenerator
import de.ruegnerlukas.sqldsl.db.pool.ConnectionPool

class PooledDatabase(private val connectionPool: ConnectionPool, codeGen: SQLCodeGenerator) : Database(codeGen) {

	override fun getConnection() = connectionPool.getConnection()

}