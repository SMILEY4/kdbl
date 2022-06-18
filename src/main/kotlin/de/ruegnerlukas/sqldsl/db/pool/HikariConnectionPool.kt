package de.ruegnerlukas.sqldsl.db.pool

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

class HikariConnectionPool(private val config: HikariConfig): ConnectionPool {

	private val dataSource = HikariDataSource(config)

	override fun getConnection(): Connection {
		return dataSource.getConnection()
	}


}