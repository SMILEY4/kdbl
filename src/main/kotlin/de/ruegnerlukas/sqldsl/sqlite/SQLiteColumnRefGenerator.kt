package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.TableAliasColumnAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.TableAliasColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.select.CountAllSelectExpression
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteColumnRefGenerator {

	fun build(ctx: GenContext, ref: ColumnRef<*, *>): Token {
		return when (ref) {
			is Column<*, *> -> directColumnRef(ctx, ref)
			is ColumnAliasRef<*, *> -> columnAliasRef(ctx, ref)
			is TableAliasColumnRef<*, *> -> tableAliasColumnRef(ctx, ref)
			is TableAliasColumnAliasRef<*, *> -> tableAliasColumnAliasRef(ctx, ref)
			is ColumnRefContainer<*> -> columnContainer(ctx, ref)
			else -> {
				throw Exception("Unknown ColumnRef: $ref")
			}
		}
	}


	private fun directColumnRef(ctx: GenContext, ref: Column<*, *>): Token {
		return StringToken(ref.getParentTable().getTableName() + "." + ref.getColumnName())
	}


	private fun columnAliasRef(ctx: GenContext, ref: ColumnAliasRef<*, *>): Token {
		if (ctx == GenContext.SELECT) {
			return StringToken("${ref.column.getColumnName()} AS ${ref.alias}")
		} else {
			return StringToken(ref.alias)
		}
	}


	private fun tableAliasColumnRef(ctx: GenContext, ref: TableAliasColumnRef<*, *>): Token {
		return StringToken("${ref.tableRef.alias}.${ref.column.getColumnName()}")
	}


	private fun tableAliasColumnAliasRef(ctx: GenContext, ref: TableAliasColumnAliasRef<*, *>): Token {
		if (ctx == GenContext.SELECT) {
			return StringToken("${ref.tableAliasColumnRef.tableRef.alias}.${ref.tableAliasColumnRef.column.getColumnName()} AS ${ref.alias}")
		} else {
			return StringToken("${ref.tableAliasColumnRef.tableRef.alias}.${ref.alias}")
		}

	}


	private fun columnContainer(ctx: GenContext, ref: ColumnRefContainer<*>): Token {
		val content = ref.getContent()
		if (content == null) {
			throw Exception("Content of column-ref-container is null")
		} else {
			return when (content) {
				is CountAllSelectExpression -> {
					if (ctx == GenContext.SELECT) {
						StringToken("COUNT(*) AS ${content.alias}")
					} else {
						StringToken(content.alias)
					}
				}
				else -> {
					throw Exception("Unknown columnRefContainer-content: ${ref.getContent()}")
				}
			}
		}
	}


}