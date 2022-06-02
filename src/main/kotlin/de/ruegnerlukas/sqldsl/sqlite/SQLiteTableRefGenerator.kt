package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.syntax.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.AliasTableRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRef
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRefContainer
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

object SQLiteTableRefGenerator {

	fun build(ctx: GenContext, ref: TableRef): Token {
		return when (ref) {
			is Table -> directTableRef(ctx, ref)
			is AliasTableRef<*> -> aliasTableRef(ctx, ref)
			is TableRefContainer -> tableRefContainer(ctx, ref)
			else -> {
				throw Exception("Unknown TableRef: $ref")
			}
		}
	}


	private fun directTableRef(ctx: GenContext, ref: Table): Token {
		return StringToken(ref.getTableName())
	}


	private fun aliasTableRef(ctx: GenContext, ref: AliasTableRef<*>): Token {
		if (ctx == GenContext.FROM) {
			return StringToken("${ref.table.getTableName()} AS ${ref.alias}")
		} else {
			return StringToken(ref.table.getTableName())
		}
	}


	private fun tableRefContainer(ctx: GenContext, ref: TableRefContainer): Token {
		val content = ref.getContent()
		if (content == null) {
			throw Exception("Content of table-ref-container is null")
		} else {
			return when (content) {
				is QueryFromExpression -> StringToken("(${SQLiteQueryGenerator.build(content.query)}) AS ${content.alias}")
				else -> {
					throw Exception("Unknown tableRefContainer-content: ${ref.getContent()}")
				}
			}
		}
	}

}