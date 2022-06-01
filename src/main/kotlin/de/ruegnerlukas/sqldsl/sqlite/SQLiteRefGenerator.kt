package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.syntax.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.TableAliasColumnAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.TableAliasColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.AliasTableRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.select.CountAllSelectExpression
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteRefGenerator {

	fun column(ref: ColumnRef<*, *>): Token {
		return when (ref) {
			is Column<*, *> -> StringToken(ref.getColumnName())
			is ColumnAliasRef<*, *> -> StringToken(ref.alias)
			is TableAliasColumnRef<*, *> -> StringToken("${ref.ref.alias}.${ref.column.getColumnName()}")
			is TableAliasColumnAliasRef<*, *> -> StringToken("${ref.ref.ref.alias}.${ref.alias}")
			is ColumnRefContainer<*> -> columnContainer(ref)
			else -> {
				throw Exception("Unknown ColumnRef: $ref")
			}
		}
	}

	private fun columnContainer(ref: ColumnRefContainer<*>): Token {
		val content = ref.getContent()
		if (content == null) {
			throw Exception("Content of column-ref-container is null")
		} else {
			return when (content) {
				is CountAllSelectExpression -> StringToken("COUNT(*) AS ${content.alias}")
				else -> {
					throw Exception("Unknown columnRefContainer-content: ${ref.getContent()}")
				}
			}
		}
	}

	fun table(ref: TableRef): Token {
		return when (ref) {
			is Table -> StringToken(ref.getTableName())
			is AliasTableRef<*> -> StringToken("${ref.table.getTableName()} AS ${ref.alias}")
			is TableRefContainer -> StringToken(ref.getContent()!!.alias)
			else -> {
				throw Exception("Unknown TableRef: $ref")
			}
		}
	}

	private fun tableContainer(ref: TableRefContainer): Token {
		val content = ref.getContent()
		if (content == null) {
			throw Exception("Content of table-ref-container is null")
		} else {
			return when (content) {
				is QueryFromExpression -> TODO()
				else -> {
					throw Exception("Unknown tableRefContainer-content: ${ref.getContent()}")
				}
			}
		}
	}

}