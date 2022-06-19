package de.ruegnerlukas.kdbl.db

import de.ruegnerlukas.kdbl.codegen.SQLCodeGenerator
import java.sql.Connection

/**
 * A database using the single given connection for all operations
 * @param connection the connection for all operations
 * @param codeGen the generator converting statements into sql-strings
 * @param sqlStringCache a map (key=id,value=sql-string) providing prebuild sql-statements, initializing the cache of this database-instance
 */
class SingleConnectionDatabase(
	private val connection: Connection,
	codeGen: SQLCodeGenerator,
	sqlStringCache: Map<String, String> = mapOf()
) : Database(codeGen, sqlStringCache) {

	override fun getConnection() = connection

}