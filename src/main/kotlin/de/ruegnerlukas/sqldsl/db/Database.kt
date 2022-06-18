package de.ruegnerlukas.sqldsl.db

import de.ruegnerlukas.sqldsl.codegen.SQLCodeGenerator
import de.ruegnerlukas.sqldsl.db.actions.DbCreate
import de.ruegnerlukas.sqldsl.db.actions.DbDelete
import de.ruegnerlukas.sqldsl.db.actions.DbInsert
import de.ruegnerlukas.sqldsl.db.actions.DbQuery
import de.ruegnerlukas.sqldsl.db.actions.DbUpdate
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

abstract class Database(private val codeGen: SQLCodeGenerator, sqlStringCache: Map<String, String> = mapOf()) {

	private val sqlStringCache: MutableMap<String, String> = sqlStringCache.toMutableMap()

	fun getSqlStringCache(): Map<String, String> = sqlStringCache


	fun startCreate(sql: String, placeholders: List<String>): DbCreate {
		return DbCreate(this, sql, placeholders)
	}


	fun startCreate(sql: CreateTableStatement): DbCreate {
		val placeholders = mutableListOf<String>()
		return startCreate(codeGen.create(sql).buildExtended(placeholders), placeholders)
	}


	fun startCreate(id: String, builder: () -> CreateTableStatement): DbCreate {
		val placeholders = mutableListOf<String>()
		return startCreate(
			sqlStringCache.getOrPut(id) {
				codeGen.create(builder()).buildExtended(placeholders)
			},
			placeholders
		)
	}


	fun startInsert(sql: String, placeholders: List<String>): DbInsert {
		return DbInsert(this, sql, placeholders)
	}


	fun startInsert(sql: SqlInsertStatement): DbInsert {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is InsertStatement -> startInsert(codeGen.insert(sql).buildExtended(placeholders), placeholders)
			is InsertBuilderEndStep -> startInsert(codeGen.insert(sql.build()).buildExtended(placeholders), placeholders)
			else -> throw IllegalStateException("Unknown insert-statement-type '${sql}'")
		}
	}


	fun startInsert(id: String, builder: () -> SqlInsertStatement): DbInsert {
		val placeholders = mutableListOf<String>()
		return startInsert(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is InsertStatement -> codeGen.insert(sql).buildExtended(placeholders)
					is InsertBuilderEndStep -> codeGen.insert(sql.build()).buildExtended(placeholders)
					else -> throw IllegalStateException("Unknown insert-statement-type '${sql}'")
				}
			},
			placeholders
		)
	}


	fun startUpdate(sql: String, placeholders: List<String>): DbUpdate {
		return DbUpdate(this, sql, placeholders)
	}


	fun startUpdate(sql: SqlUpdateStatement): DbUpdate {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is UpdateStatement -> startUpdate(codeGen.update(sql).buildExtended(placeholders), placeholders)
			is UpdateBuilderEndStep -> startUpdate(codeGen.update(sql.build()).buildExtended(placeholders), placeholders)
			else -> throw IllegalStateException("Unknown update-statement-type '${sql}'")
		}
	}


	fun startUpdate(id: String, builder: () -> SqlUpdateStatement): DbUpdate {
		val placeholders = mutableListOf<String>()
		return startUpdate(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is UpdateStatement -> codeGen.update(sql).buildExtended(placeholders)
					is UpdateBuilderEndStep -> codeGen.update(sql.build()).buildExtended(placeholders)
					else -> throw IllegalStateException("Unknown update-statement-type '${sql}'")
				}
			},
			placeholders
		)
	}


	fun startDelete(sql: String, placeholders: List<String>): DbDelete {
		return DbDelete(this, sql, placeholders)
	}


	fun startDelete(sql: SqlDeleteStatement): DbDelete {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is DeleteStatement -> startDelete(codeGen.delete(sql).buildExtended(placeholders), placeholders)
			is DeleteBuilderEndStep -> startDelete(codeGen.delete(sql.build()).buildExtended(placeholders), placeholders)
			else -> throw IllegalStateException("Unknown delete-statement-type '${sql}'")
		}
	}


	fun startDelete(id: String, builder: () -> SqlDeleteStatement): DbDelete {
		val placeholders = mutableListOf<String>()
		return startDelete(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is DeleteStatement -> codeGen.delete(sql).buildExtended(placeholders)
					is DeleteBuilderEndStep -> codeGen.delete(sql.build()).buildExtended(placeholders)
					else -> throw IllegalStateException("Unknown delete-statement-type '${sql}'")
				}
			},
			placeholders
		)
	}


	fun startQuery(sql: String, placeholders: List<String>): DbQuery {
		return DbQuery(this, sql, placeholders)
	}


	fun startQuery(sql: SqlQueryStatement<*>): DbQuery {
		val placeholders = mutableListOf<String>()
		return when (sql) {
			is QueryStatement<*> -> startQuery(codeGen.query(sql).buildExtended(placeholders), placeholders)
			is QueryBuilderEndStep<*> -> startQuery(codeGen.query(sql.build<Any>()).buildExtended(placeholders), placeholders)
			else -> throw IllegalStateException("Unknown query-statement-type '${sql}'")
		}
	}


	fun startQuery(id: String, builder: () -> SqlQueryStatement<*>): DbQuery {
		val placeholders = mutableListOf<String>()
		return startQuery(
			sqlStringCache.getOrPut(id) {
				when (val sql = builder()) {
					is QueryStatement<*> -> codeGen.query(sql).buildExtended(placeholders)
					is QueryBuilderEndStep<*> -> codeGen.query(sql.build<Any>()).buildExtended(placeholders)
					else -> throw IllegalStateException("Unknown query-statement-type '${sql}'")
				}
			},
			placeholders
		)
	}


	fun startTransaction(withRollback: Boolean, block: (db: Database) -> Unit) {
		getConnection().use {
			try {
				val transaction = SingleConnectionDatabase(it, codeGen)
				it.autoCommit = false
				block(transaction)
				it.commit()
				transaction.getSqlStringCache().forEach { key, value ->
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


	fun execute(sql: String, params: List<Any>) {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			statement.execute()
		}
	}


	fun executeQuery(sql: String, params: List<Any>): ResultSet {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			return statement.executeQuery()
		}
	}


	fun executeUpdate(sql: String, params: List<Any>): Int {
		getConnection().use {
			val statement = it.prepareStatement(sql)
			setParameters(statement, params)
			return statement.executeUpdate()
		}
	}


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


	abstract fun getConnection(): Connection

}