package de.ruegnerlukas.sqldsl.db

import de.ruegnerlukas.sqldsl.codegen.SQLCodeGenerator
import java.sql.Connection

class SingleConnectionDatabase(
	private val connection: Connection,
	codeGen: SQLCodeGenerator,
	sqlStringCache: Map<String, String> = mapOf()
) : Database(codeGen, sqlStringCache) {


	override fun getConnection() = connection

}