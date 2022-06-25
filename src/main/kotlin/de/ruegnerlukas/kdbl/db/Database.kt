package de.ruegnerlukas.kdbl.db

import de.ruegnerlukas.kdbl.codegen.SQLCodeGenerator
import de.ruegnerlukas.kdbl.db.actions.DbCreate
import de.ruegnerlukas.kdbl.db.actions.DbModification
import de.ruegnerlukas.kdbl.db.actions.DbQuery
import de.ruegnerlukas.kdbl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.kdbl.dsl.statements.DeleteBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.DeleteStatement
import de.ruegnerlukas.kdbl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.InsertStatement
import de.ruegnerlukas.kdbl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.QueryStatement
import de.ruegnerlukas.kdbl.dsl.statements.SqlDeleteStatement
import de.ruegnerlukas.kdbl.dsl.statements.SqlInsertStatement
import de.ruegnerlukas.kdbl.dsl.statements.SqlQueryStatement
import de.ruegnerlukas.kdbl.dsl.statements.SqlUpdateStatement
import de.ruegnerlukas.kdbl.dsl.statements.UpdateBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.UpdateStatement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

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
	fun startInsert(sql: String, placeholders: List<String>): DbModification {
		return DbModification(this, sql, placeholders)
	}


	/**
	 * Start an INSERT-statement
	 * @param builder a function providing the "insert"-statement
	 */
	fun startInsert(builder: () -> SqlInsertStatement): DbModification {
		val placeholders = mutableListOf<String>()
		return when (val sql = builder()) {
			is InsertStatement -> startInsert(codeGen.insert(sql).buildExtended(placeholders), placeholders)
			is InsertBuilderEndStep -> startInsert(codeGen.insert(sql.build()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start an INSERT-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "insert"-statement
	 */
	fun startInsert(id: String, builder: () -> SqlInsertStatement): DbModification {
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
	 * INSERT-statement in batches
	 * @param batchSize the (max) size of one batch
	 * @param items the items to insert
	 * @param builder a function providing the "insert"-statement for a given batch
	 */
	suspend fun <T> insertBatched(batchSize: Int, items: List<T>, builder: (batch: List<T>) -> SqlInsertStatement) {
		startTransaction(true) { tdb ->
			items.chunked(batchSize).forEach { batch ->
				tdb.startInsert { builder(batch) }
			}
		}
	}


	/**
	 * Start an UPDATE-statement
	 * @param sql the sql-statement
	 * @param placeholders the names of placeholders in the sql-string in the correct order
	 */
	fun startUpdate(sql: String, placeholders: List<String>): DbModification {
		return DbModification(this, sql, placeholders)
	}


	/**
	 * Start an UPDATE-statement
	 * @param builder a function providing the "update"-statement
	 */
	fun startUpdate(builder: () -> SqlUpdateStatement): DbModification {
		val placeholders = mutableListOf<String>()
		return when (val sql = builder()) {
			is UpdateStatement -> startUpdate(codeGen.update(sql).buildExtended(placeholders), placeholders)
			is UpdateBuilderEndStep -> startUpdate(codeGen.update(sql.build()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start an UPDATE-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "update"-statement
	 */
	fun startUpdate(id: String, builder: () -> SqlUpdateStatement): DbModification {
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
	fun startDelete(sql: String, placeholders: List<String>): DbModification {
		return DbModification(this, sql, placeholders)
	}


	/**
	 * Start a DELETE-statement
	 * @param builder a function providing the "delete"-statement
	 */
	fun startDelete(builder: () -> SqlDeleteStatement): DbModification {
		val placeholders = mutableListOf<String>()
		return when (val sql = builder()) {
			is DeleteStatement -> startDelete(codeGen.delete(sql).buildExtended(placeholders), placeholders)
			is DeleteBuilderEndStep -> startDelete(codeGen.delete(sql.build()).buildExtended(placeholders), placeholders)
		}
	}


	/**
	 * Start a DELETE-statement
	 * @param id the unique id of the statement. The statement is only converted into a string the first time and is then reused
	 * @param builder a function providing the "delete"-statement
	 */
	fun startDelete(id: String, builder: () -> SqlDeleteStatement): DbModification {
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
	 * @param builder a function providing the "select"-statement
	 */
	fun startQuery(builder: () -> SqlQueryStatement<*>): DbQuery {
		val placeholders = mutableListOf<String>()
		return when (val sql = builder()) {
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
	suspend fun startTransaction(withRollback: Boolean, block: suspend (db: Database) -> Unit) {
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
	suspend fun execute(sql: String, params: List<Any?>) {
		withContext(Dispatchers.IO) {
			getConnection().use {
				val statement = it.prepareStatement(sql)
				setParameters(statement, params)
				statement.execute()
			}
		}
	}


	/**
	 * Execute the sql-operation and expect result (e.g. an "insert"-statement with a "returning"-clause)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the result of the operation
	 */
	suspend fun executeReturning(sql: String, params: List<Any?>): ResultSet {
		return withContext(Dispatchers.IO) {
			getConnection().use {
				val statement = it.prepareStatement(sql)
				setParameters(statement, params)
				statement.execute()
				statement.resultSet
			}
		}
	}


	/**
	 * Execute the sql-query (i.e. a "SELECT"-Statement)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the result of the query
	 */
	suspend fun executeQuery(sql: String, params: List<Any?>): ResultSet {
		return withContext(Dispatchers.IO) {
			getConnection().use {
				val statement = it.prepareStatement(sql)
				setParameters(statement, params)
				statement.executeQuery()
			}
		}
	}


	/**
	 * Execute the sql-update (i.e. a "INSERT", "UPDATE" or "DELETE"-statement)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the number of affected rows
	 */
	suspend fun executeUpdate(sql: String, params: List<Any?>): Int {
		return withContext(Dispatchers.IO) {
			getConnection().use {
				val statement = it.prepareStatement(sql)
				setParameters(statement, params)
				statement.executeUpdate()
			}
		}
	}


	/**
	 * Set the values of parameters of the given statement
	 * @param statement the sql-statement
	 * @param params the list of values in the correct order
	 */
	private fun setParameters(statement: PreparedStatement, params: List<Any?>) {
		params.forEachIndexed { index, param ->
			when (param) {
				is Short -> statement.setShort(index, param)
				is Int -> statement.setInt(index, param)
				is Long -> statement.setLong(index, param)
				is Float -> statement.setFloat(index, param)
				is Double -> statement.setDouble(index, param)
				is Boolean -> statement.setBoolean(index, param)
				is String -> statement.setString(index, param)
				null -> statement.setNull(index, Types.NULL)
				else -> throw Exception("Unknown parameter-type: '$param'")
			}
		}
	}


	/**
	 * @return a (new) connection
	 */
	abstract fun getConnection(): Connection

}