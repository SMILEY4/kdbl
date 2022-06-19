package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.utils.map
import java.sql.ResultSet

/**
 * The result of a single "SELECT" or update operation with a "RETURNING"-clause
 * @param result the [ResultSet]
 */
class DbReturningResult(private val result: ResultSet) {

	/**
	 * Map each row of the result and return all as a list
	 * @param mapper the mapper for a single row
	 * @return the list of all mapped rows
	 */
	fun <T> mapRows(mapper: (row: ResultSet) -> T): List<T> {
		return result.map(mapper)
	}

}