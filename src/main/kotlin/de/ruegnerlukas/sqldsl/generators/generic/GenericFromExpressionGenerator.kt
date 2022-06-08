package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.FromExpressionGenerator
import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.grammar.from.JoinFromExpression
import de.ruegnerlukas.sqldsl.grammar.from.QueryFromExpression
import de.ruegnerlukas.sqldsl.grammar.from.TableFromExpression
import de.ruegnerlukas.sqldsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.table.AliasTable
import de.ruegnerlukas.sqldsl.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl.grammar.table.StandardTable
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.StringToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericFromExpressionGenerator(private val genCtx: GeneratorContext) : FromExpressionGenerator,
	GenericGeneratorBase<FromExpression>() {

	override fun buildToken(e: FromExpression): Token {
		return when (e) {
			is TableFromExpression -> table(e)
			is JoinFromExpression -> join(e)
			is QueryFromExpression -> query(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun table(e: TableFromExpression): Token {
		return when (e) {
			is AliasTable -> aliasTable(e)
			is DerivedTable -> derivedTable(e)
			is StandardTable -> standardTable(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun aliasTable(e: AliasTable): Token {
		return ListToken()
			.add(table(e.baseTable))
			.add("AS")
			.add(e.aliasName)
	}

	protected fun derivedTable(e: DerivedTable): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.getContent())))
			.add("AS")
			.add(e.tableName)
	}

	protected fun standardTable(e: StandardTable): Token {
		return StringToken(e.tableName)
	}

	protected fun join(e: JoinFromExpression): Token {
		return when (e) {
			is JoinClause -> genCtx.joinClause().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun query(e: QueryFromExpression): Token {
		return when (e) {
			is QueryStatement<*> -> ListToken().add(GroupToken(genCtx.query().buildToken(e)))
			else -> throwUnknownType(e)
		}
	}

}