package de.ruegnerlukas.sqldsl.core.generators

import de.ruegnerlukas.sqldsl.core.actions.query.QueryStatement

interface QueryGenerator {
	fun build(stmt: QueryStatement): String
}