package de.ruegnerlukas.sqldsl.core.grammar.refs.table

import de.ruegnerlukas.sqldsl.core.grammar.from.QueryFromExpression

class TableRefContainer : TableRef {

	private var content: QueryFromExpression? = null

	fun fill(query: QueryFromExpression): TableRef {
		return this
	}

	fun getContent(): QueryFromExpression? {
		return content
	}

}