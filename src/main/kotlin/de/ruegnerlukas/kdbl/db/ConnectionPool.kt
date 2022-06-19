package de.ruegnerlukas.kdbl.db

import java.sql.Connection

/**
 * A pool providing db-connections
 */
interface ConnectionPool {

	/**
	 * Return a new/pooled connection
	 */
	fun getConnection(): Connection

}