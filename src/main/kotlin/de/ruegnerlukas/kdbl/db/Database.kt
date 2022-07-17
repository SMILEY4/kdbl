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
 * @param cache the reusable sql-strings and placeholders, initializing the cache of this database-instance
 */
abstract class Database(private val codeGen: SQLCodeGenerator, private val cache: SqlCache = SqlCache()) {

	/**
	 * @return the store for reusable sql-strings and placeholders
	 */
	fun getSqlStringCache() = cache


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
		val statement = cache.getOrPut(id) {
			val placeholders = mutableListOf<String>()
			val sql = codeGen.create(builder()).buildExtended(placeholders)
			sql to placeholders
		}
		return startCreate(statement.first, statement.second)
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
		val statement = cache.getOrPut(id) {
			val placeholders = mutableListOf<String>()
			val sql = when (val v = builder()) {
				is InsertStatement -> codeGen.insert(v).buildExtended(placeholders)
				is InsertBuilderEndStep -> codeGen.insert(v.build()).buildExtended(placeholders)
			}
			sql to placeholders
		}
		return startInsert(statement.first, statement.second)
	}


	/**
	 * INSERT-statement in batches
	 * @param transaction whether to insert all batches in a new transaction
	 * @param batchSize the (max) size of one batch
	 * @param items the items to insert
	 * @param builder a function providing the "insert"-statement for a given batch
	 */
	suspend fun <T> insertBatched(transaction: Boolean, batchSize: Int, items: List<T>, builder: (batch: List<T>) -> SqlInsertStatement) {
		return when (transaction) {
			true -> insertBatchedTransaction(batchSize, items, builder)
			false -> insertBatched(batchSize, items, builder)
		}
	}


	/**
	 * INSERT-statement in batches
	 * @param batchSize the (max) size of one batch
	 * @param items the items to insert
	 * @param builder a function providing the "insert"-statement for a given batch
	 */
	suspend fun <T> insertBatched(batchSize: Int, items: List<T>, builder: (batch: List<T>) -> SqlInsertStatement) {
		items.chunked(batchSize).forEach { batch ->
			startInsert { builder(batch) }.execute()
		}
	}


	/**
	 * INSERT-statement in batches in a new transaction
	 * @param batchSize the (max) size of one batch
	 * @param items the items to insert
	 * @param builder a function providing the "insert"-statement for a given batch
	 */
	suspend fun <T> insertBatchedTransaction(batchSize: Int, items: List<T>, builder: (batch: List<T>) -> SqlInsertStatement) {
		startTransaction(true) { tdb ->
			items.chunked(batchSize).forEach { batch ->
				tdb.startInsert { builder(batch) }.execute()
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
		val statement = cache.getOrPut(id) {
			val placeholders = mutableListOf<String>()
			val sql = when (val v = builder()) {
				is UpdateStatement -> codeGen.update(v).buildExtended(placeholders)
				is UpdateBuilderEndStep -> codeGen.update(v.build()).buildExtended(placeholders)
			}
			sql to placeholders
		}
		return startUpdate(statement.first, statement.second)
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
		val statement = cache.getOrPut(id) {
			val placeholders = mutableListOf<String>()
			val sql = when (val v = builder()) {
				is DeleteStatement -> codeGen.delete(v).buildExtended(placeholders)
				is DeleteBuilderEndStep -> codeGen.delete(v.build()).buildExtended(placeholders)
			}
			sql to placeholders
		}
		return startDelete(statement.first, statement.second)
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
		val statement = cache.getOrPut(id) {
			val placeholders = mutableListOf<String>()
			val sql = when (val v = builder()) {
				is QueryStatement<*> -> codeGen.query(v).buildExtended(placeholders)
				is QueryBuilderEndStep<*> -> codeGen.query(v.build<Any>()).buildExtended(placeholders)
			}
			sql to placeholders
		}
		return startQuery(statement.first, statement.second)
	}


	/**
	 * Start a transaction.
	 * @param withRollback whether the operations should be rolled back on failure
	 * @param block the block with the sql-operations. A [Database] is provided that executes all its operations as part of the transaction
	 */
	suspend fun startTransaction(withRollback: Boolean, block: suspend (db: Database) -> Unit) {
		getConnection {
			try {
				val transaction = SingleConnectionDatabase(it, codeGen)
				it.autoCommit = false
				block(transaction)
				it.commit()
				cache.put(transaction.getSqlStringCache())
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
			getStatement(sql) { statement ->
				setParameters(statement, params)
				statement.execute()
			}
		}
	}


	/**
	 * Execute the sql-operation and expect result (e.g. a "select"-statement or an "insert"-statement with a "returning"-clause)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the result of the operation
	 */
	suspend fun executeReturning(sql: String, params: List<Any?>): ResultSet {
		return withContext(Dispatchers.IO) {
			getStatement(sql) { statement ->
				setParameters(statement, params)
				statement.execute()
				statement.resultSet
			}
		}
	}


	/**
	 * Execute the sql-operation and expect result (e.g. a "select"-statement or an "insert"-statement with a "returning"-clause)
	 * @param sql the sql-string
	 * @param params the list of parameters in the correct order
	 * @return the result of the operation
	 */
	suspend fun <R> executeReturning(sql: String, params: List<Any?>, consumer: (resultSet: ResultSet) -> R): R {
		return withContext(Dispatchers.IO) {
			getStatement(sql) { statement ->
				setParameters(statement, params)
				statement.execute()
				consumer(statement.resultSet)
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
			getStatement(sql) { statement ->
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
				is Short -> statement.setShort(index + 1, param)
				is Int -> statement.setInt(index + 1, param)
				is Long -> statement.setLong(index + 1, param)
				is Float -> statement.setFloat(index + 1, param)
				is Double -> statement.setDouble(index + 1, param)
				is Boolean -> statement.setBoolean(index + 1, param)
				is String -> statement.setString(index + 1, param)
				null -> statement.setNull(index + 1, Types.NULL)
				else -> throw Exception("Unknown parameter-type: '$param'")
			}
		}
	}


	private suspend fun <R> getStatement(sql: String, block: suspend (statement: PreparedStatement) -> R): R {
		return getConnection { connection ->
			val statement = connection.prepareStatement(sql)
			statement.use {
				block(statement)
			}
		}
	}


	/**
	 * @return a (new) connection
	 */
	abstract suspend fun <R> getConnection(block: suspend (connection: Connection) -> R): R

}