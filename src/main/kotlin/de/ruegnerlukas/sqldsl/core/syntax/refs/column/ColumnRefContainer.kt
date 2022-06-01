package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.syntax.select.FunctionSelectExpression
import de.ruegnerlukas.sqldsl.core.schema.UnknownTable

class ColumnRefContainer<D> : ColumnRef<D, UnknownTable> {

	private var content: FunctionSelectExpression? = null

	fun fill(func: FunctionSelectExpression): ColumnRef<D, UnknownTable> {
		return this
	}

	fun getContent(): FunctionSelectExpression? {
		return content
	}

}