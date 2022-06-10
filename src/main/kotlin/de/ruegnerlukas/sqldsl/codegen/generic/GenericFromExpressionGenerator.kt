package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.FromExpressionGenerator
import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericFromExpressionGenerator(private val genCtx: GeneratorContext) : FromExpressionGenerator,
	GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.from.TableFromExpression -> table(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.from.JoinFromExpression -> join(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.from.QueryFromExpression -> query(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun table(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.TableFromExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.table.AliasTable -> aliasTable(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable -> derivedTable(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.table.StandardTable -> standardTable(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun aliasTable(e: de.ruegnerlukas.sqldsl.dsl.grammar.table.AliasTable): Token {
		return ListToken()
			.add(table(e.baseTable))
			.add("AS")
			.add(e.aliasName)
	}

	protected fun derivedTable(e: de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable): Token {
		return ListToken()
			.add(GroupToken(buildToken(e.getContent())))
			.add("AS")
			.add(e.tableName)
	}

	protected fun standardTable(e: de.ruegnerlukas.sqldsl.dsl.grammar.table.StandardTable): Token {
		return StringToken(e.tableName)
	}

	protected fun join(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.JoinFromExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause -> genCtx.joinClause().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun query(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.QueryFromExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*> -> ListToken().add(GroupToken(genCtx.query().buildToken(e)))
			else -> throwUnknownType(e)
		}
	}

}