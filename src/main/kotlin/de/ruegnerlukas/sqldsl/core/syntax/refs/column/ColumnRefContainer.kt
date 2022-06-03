package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.schema.UnknownTable

class ColumnRefContainer<D> : ColumnRef<D, UnknownTable> {

	private var content: ExpressionAliasRef<*>? = null

	fun fill(e: ExpressionAliasRef<*>): ColumnRef<D, UnknownTable> {
		this.content = e
		return this
	}

	fun getContent(): ExpressionAliasRef<*>? {
		return content
	}

}