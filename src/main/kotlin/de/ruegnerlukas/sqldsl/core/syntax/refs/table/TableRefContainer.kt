package de.ruegnerlukas.sqldsl.core.syntax.refs.table

import de.ruegnerlukas.sqldsl.core.syntax.from.QueryFromExpression

class TableRefContainer : TableRef {

	private var content: QueryFromExpression? = null

	fun fill(query: QueryFromExpression): TableRef {
		return this
	}

	fun getContent(): QueryFromExpression? {
		return content
	}

}