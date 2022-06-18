package de.ruegnerlukas.sqldsl.db.pool

import java.sql.Connection

interface ConnectionPool {
	fun getConnection(): Connection
}