package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.LiteralGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericLiteralValueGenerator(private val genCtx: GeneratorContext) : LiteralGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral -> intLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral -> floatLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral -> stringLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.BooleanLiteral -> booleanLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral -> listLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.NullLiteral -> nullLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.MacroLiteral -> macroLiteral(e)
			is de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubQueryLiteral -> subQueryLiteral(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun intLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral): Token {
		return StringToken(e.value.toString())
	}

	protected fun floatLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.FloatLiteral): Token {
		return StringToken(e.value.toString())
	}

	protected fun stringLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral): Token {
		return StringToken("'${e.value}'")
	}

	protected fun booleanLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.BooleanLiteral): Token {
		return StringToken(if (e.value) "TRUE" else "FALSE")
	}

	protected fun listLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ListLiteral<*>): Token {
		return GroupToken(
			CsvListToken(
				e.values.map { buildToken(it) }
			)
		)
	}

	protected fun nullLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.NullLiteral): Token {
		return StringToken("NULL")
	}

	protected fun macroLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.MacroLiteral): Token {
		return when (e.value) {
			de.ruegnerlukas.sqldsl.dsl.grammar.expr.MacroLiteral.Companion.Value.CURRENT_TIMESTAMP -> StringToken("CURRENT_TIMESTAMP")
		}
	}

	protected fun subQueryLiteral(e: de.ruegnerlukas.sqldsl.dsl.grammar.expr.SubQueryLiteral<*>): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*> -> genCtx.query().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}