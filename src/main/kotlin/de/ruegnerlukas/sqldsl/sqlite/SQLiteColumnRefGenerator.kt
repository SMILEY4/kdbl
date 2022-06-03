package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.AliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ExpressionAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.TableAliasColumnAliasRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.TableAliasColumnRef
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteColumnRefGenerator {

	fun buildString(ctx: GenContext, ref: ColumnRef<*, *>): String {
		return build(ctx, ref).buildString()
	}

	fun build(ctx: GenContext, ref: ColumnRef<*, *>): Token {
		return when (ref) {
			is Column<*, *> -> directColumnRef(ctx, ref)
			is AliasRef<*, *> -> aliasRef(ctx, ref)
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


	private fun aliasRef(ctx: GenContext, ref: AliasRef<*, *>): Token {
		return when (ref) {
			is ColumnAliasRef<*, *> -> {
				if (ctx == GenContext.SELECT) {
					StringToken("${ref.column.getColumnName()} AS ${ref.alias}")
				} else {
					StringToken(ref.alias)
				}
			}
			is ExpressionAliasRef<*> -> {
				if (ctx == GenContext.SELECT) {
					StringToken("${SQLiteExpressionGenerator.build(ref.expression)} AS ${ref.alias}")
				} else {
					StringToken(ref.alias)
				}
			}
			else -> {
				throw Exception("Unknown AliasRef: $ref")
			}
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
			if (ctx == GenContext.SELECT) {
				return StringToken("(${SQLiteExpressionGenerator.build(content.expression)}) AS ${content.alias}")
			} else {
				return StringToken(content.alias)
			}
		}
	}


}