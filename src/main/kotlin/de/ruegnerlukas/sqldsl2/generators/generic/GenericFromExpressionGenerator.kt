package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.FromExpressionGenerator
import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.grammar.TableAlias
import de.ruegnerlukas.sqldsl2.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.JoinFromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.QueryFromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.TableAliasFromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.TableFromExpression
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.tokens.GroupToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericFromExpressionGenerator(private val genCtx: GeneratorContext) : FromExpressionGenerator, GenericGeneratorBase<FromExpression>() {

	override fun buildToken(e: FromExpression): Token {
		return when (e) {
			is TableAliasFromExpression -> tableAlias(e)
			is TableFromExpression -> table(e)
			is JoinFromExpression -> join(e)
			is QueryFromExpression -> query(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun table(e: TableFromExpression): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
	}

	protected fun tableAlias(e: TableAliasFromExpression): Token {
		return when (e) {
			is TableAlias -> ListToken().add(e.tableName).add("AS").add(e.alias)
			else -> throwUnknownType(e)
		}
	}

	protected fun join(e: JoinFromExpression): Token {
		return when (e) {
			is JoinClause -> genCtx.joinClause().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun query(e: QueryFromExpression): Token {
		return ListToken()
			.add(GroupToken(genCtx.query().buildToken(e.query)))
			.add("AS")
			.add(e.alias)
	}

}