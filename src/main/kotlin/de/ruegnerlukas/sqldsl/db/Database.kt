package de.ruegnerlukas.sqldsl.db

import de.ruegnerlukas.sqldsl.codegen.SQLCodeGenerator
import de.ruegnerlukas.sqldsl.db.actions.DbCreate
import de.ruegnerlukas.sqldsl.db.actions.DbDeleteReturnType
import de.ruegnerlukas.sqldsl.db.actions.DbInsertReturnType
import de.ruegnerlukas.sqldsl.db.actions.DbQuery
import de.ruegnerlukas.sqldsl.db.actions.DbUpdateReturnType
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SqlDeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SqlInsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SqlQueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SqlUpdateStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * The base of a database with the basic functions
 * @param codeGen the generator converting statements into sql-strings
 * @param sqlStringCache a map (key=id,value=sql-string) providing prebuild sql-statements, initializing the cache of this database-instance
 */
abstract class Database(private val codeGen: SQLCodeGenerator, sqlStringCache: Map<String, String> = mapOf()) {

	private val sqlStringCache: MutableMap<String, String> = sqlStringCache.toMutableMap()


	/**
	 * @return the map with build sql-strings (key=id,value=sql-string)
	 */
	fun getSqlStringCache(): Map<String, String> = sqlStringCache


	/**
	 * Start a CREATE-statement
	 * @param sql the sql-statement
	 * @param placeholders the names of placeholders in the sql-string in the correct order
	 */
	fun startCreate(sql: String, placeholders: List<String>): DbCreate {
		return DbCreate(this, sql, placeholders)
	}


	/**
	 * Start a CREATE-statement
	 * @param sql the "create-table"-statement
	 */
	fun startCreate(sql: CreateTableStatement): DbCreate {
		val placeholders = mutableListOf<String>()
		return startCreate(codeGen.create(sql).buildExtended(placeholders), placeholders)
	}


	/**
	 * Start a CREATE-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "create-table"-statement
	 */
	fun startCreate(id: String, builder: () -> CreateTableStatement): DbCreate {
		val placeholders = mutableListOf<String>()
		return startCreate(
			sqlStringCache.getOrPut(id) {
				codeGen.create(builder()).buildExtended(placeholders)
			},
			placeholders
		)
	}


	/**
	 * Start an INSERT-statement
	 * @param sql the sql-statement
	 * @param placeholders the names of placeholders in the sql-string in the correct order
	 */
	fun startInsert(sql: String, placeholders: List<String>): DbInsertReturnType {
		return DbInsertReturnType(this, sql, placeholders)
	}


	/**
	 * Start an INSERT-statement
	 * @param sql the "insert"-statement
	 */
	fun startInsert(sql: SqlInsertStatement): DbInsertReturnType {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is InsertStatement -> startInsert(codeGen.insert(sql).buildExtended(placeholders), placeholders)
			is InsertBuilderEndStep -> startInsert(codeGen.insert(sql.build()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start an INSERT-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "insert"-statement
	 */
	fun startInsert(id: String, builder: () -> SqlInsertStatement): DbInsertReturnType {
		val placeholders = mutableListOf<String>()
		return startInsert(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is InsertStatement -> codeGen.insert(sql).buildExtended(placeholders)
					is InsertBuilderEndStep -> codeGen.insert(sql.build()).buildExtended(placeholders)
				}
			},
			placeholders
		)
	}


	/**
	 * Start an UPDATE-statement
	 * @param sql the sql-statement
	 * @param placeholders the names of placeholders in the sql-string in the correct order
	 */
	fun startUpdate(sql: String, placeholders: List<String>): DbUpdateReturnType {
		return DbUpdateReturnType(this, sql, placeholders)
	}


	/**
	 * Start an UPDATE-statement
	 * @param sql the "update"-statement
	 */
	fun startUpdate(sql: SqlUpdateStatement): DbUpdateReturnType {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is UpdateStatement -> startUpdate(codeGen.update(sql).buildExtended(placeholders), placeholders)
			is UpdateBuilderEndStep -> startUpdate(codeGen.update(sql.build()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start an UPDATE-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "update"-statement
	 */
	fun startUpdate(id: String, builder: () -> SqlUpdateStatement): DbUpdateReturnType {
		val placeholders = mutableListOf<String>()
		return startUpdate(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is UpdateStatement -> codeGen.update(sql).buildExtended(placeholders)
					is UpdateBuilderEndStep -> codeGen.update(sql.build()).buildExtended(placeholders)
				}
			},
			placeholders
		)
	}


	/**
	 * Start a DELETE-statement
	 * @param sql the sql-statement
	 * @param placeholders the names of placeholders in the sql-string in the correct order
	 */
	fun startDelete(sql: String, placeholders: List<String>): DbDeleteReturnType {
		return DbDeleteReturnType(this, sql, placeholders)
	}


	/**
	 * Start a DELETE-statement
	 * @param sql the "delete"-statement
	 */
	fun startDelete(sql: SqlDeleteStatement): DbDeleteReturnType {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is DeleteStatement -> startDelete(codeGen.delete(sql).buildExtended(placeholders), placeholders)
			is DeleteBuilderEndStep -> startDelete(codeGen.delete(sql.build()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start a DELETE-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "delete"-statement
	 */
	fun startDelete(id: String, builder: () -> SqlDeleteStatement): DbDeleteReturnType {
		val placeholders = mutableListOf<String>()
		return startDelete(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is DeleteStatement -> codeGen.delete(sql).buildExtended(placeholders)
					is DeleteBuilderEndStep -> codeGen.delete(sql.build()).buildExtended(placeholders)
				}
			},
			placeholders
		)
	}


	/**
	 * Start a SELECT-statement
	 * @param sql the sql-statement
	 * @param placeholders the names of placeholders in the sql-string in the correct order
	 */
	fun startQuery(sql: String, placeholders: List<String>): DbQuery {
		return DbQuery(this, sql, placeholders)
	}


	/**
	 * Start a SELECT-statement
	 * @param sql the "select"-statement
	 */
	fun startQuery(sql: SqlQueryStatement<*>): DbQuery {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is QueryStatement<*> -> startQuery(codeGen.query(sql).buildExtended(placeholders), placeholders)
			is QueryBuilderEndStep<*> -> startQuery(codeGen.query(sql.build<Any>()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start a SELECT-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "select"-statement
	 */
	fun startQuery(id: String, builder: () -> SqlQueryStatement<*>): DbQuery {
		val placeholders = mutableListOf<String>()
		return startQuery(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is QueryStatement<*> -> codeGen.query(sql).buildExtended(placeholders)
					is QueryBuilderEndStep<*> -> codeGen.query(sql.build<Any>()).buildExtended(placeholders)
				}
			},
			placeholders
		)
	}


	/**
	 * Start a transaction.
	 * @param withRollback whether the operations should be rolled back on failure
	 * @param block the block with the sql-operations. A [Database] is provided that executes all its operations as part of the transaction
	 */
	fun startTransaction(withRollback: Boolean, block: (db: Database) -> Unit) {
		getConnection().use {
			try {
				val transaction = SingleConnectionDatabase(it, codeGen)
				it.autoCommit = false
				block(transaction)
				it.commit()
				transaction.getSqlStringCache().forEach { (key, value) ->
					this.sqlStringCache.putIfAbsent(key, value)
				}
			} catch (e: Exception) {
				if (withRollback) {
					it.rollback()
				}
			} finally {
				it.autoCommit = true
			}
		}
	}


	/**
	 * Execute the sql-operation and expect no result
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 */
	fun execute(sql: String, params: List<Any>) {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			statement.execute()
		}
	}


	/**
	 * Execute the sql-operation and expect result (e.g. an "insert"-statement with a "returning"-clause)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the result of the operation
	 */
	fun executeReturning(sql: String, params: List<Any>): ResultSet {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			statement.execute()
			return statement.resultSet
		}
	}


	/**
	 * Execute the sql-query (i.e. a "SELECT"-Statement)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the result of the query
	 */
	fun executeQuery(sql: String, params: List<Any>): ResultSet {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			return statement.executeQuery()
		}
	}


	/**
	 * Execute the sql-update (i.e. a "INSERT", "UPDATE" or "DELETE"-statement)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the number of affected rows
	 */
	fun executeUpdate(sql: String, params: List<Any>): Int {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			return statement.executeUpdate()
		}
	}


	/**
	 * Set the values of parameters of the given statement
	 * @param statement the sql-statement
	 * @param params the list of values in the correct order
	 */
	private fun setParameters(statement: PreparedStatement, params: List<Any>) {
		params.forEachIndexed { index, param ->
			when (param) {
				is Short -> statement.setShort(index, param)
				is Int -> statement.setInt(index, param)
				is Long -> statement.setLong(index, param)
				is Float -> statement.setFloat(index, param)
				is Double -> statement.setDouble(index, param)
				is Boolean -> statement.setBoolean(index, param)
				is String -> statement.setString(index, param)
				else -> throw Exception("Unknown parameter-type: '$param'")
			}
		}
	}


	/**
	 * @return a (new) connection
	 */
	abstract fun getConnection(): Connection

}