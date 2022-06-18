package de.ruegnerlukas.sqldsl.db.actions

import de.ruegnerlukas.sqldsl.utils.map
import java.sql.ResultSet

class DbQueryResult(private val result: ResultSet) {

	fun <T> mapRows(mapper: (row: ResultSet) -> T): List<T> {
		return result.map(mapper)
	}

}