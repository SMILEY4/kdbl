package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.generators.LiteralGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.BooleanLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.FloatLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.ListLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.LiteralValue
import de.ruegnerlukas.sqldsl.grammar.expr.MacroLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.NullLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.SubQueryLiteral
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.tokens.GroupToken
import de.ruegnerlukas.sqldsl.tokens.StringToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericLiteralValueGenerator(private val genCtx: GeneratorContext) : LiteralGenerator, GenericGeneratorBase<LiteralValue<*>>() {

	override fun buildToken(e: LiteralValue<*>): Token {
		return when (e) {
			is IntLiteral -> intLiteral(e)
			is FloatLiteral -> floatLiteral(e)
			is StringLiteral -> stringLiteral(e)
			is BooleanLiteral -> booleanLiteral(e)
			is ListLiteral -> listLiteral(e)
			is NullLiteral -> nullLiteral(e)
			is MacroLiteral -> macroLiteral(e)
			is SubQueryLiteral -> subQueryLiteral(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun intLiteral(e: IntLiteral): Token {
		return StringToken(e.value.toString())
	}

	protected fun floatLiteral(e: FloatLiteral): Token {
		return StringToken(e.value.toString())
	}

	protected fun stringLiteral(e: StringLiteral): Token {
		return StringToken("'${e.value}'")
	}

	protected fun booleanLiteral(e: BooleanLiteral): Token {
		return StringToken(if (e.value) "TRUE" else "FALSE")
	}

	protected fun listLiteral(e: ListLiteral<*>): Token {
		return GroupToken(
			CsvListToken(
				e.values.map { buildToken(it) }
			)
		)
	}

	protected fun nullLiteral(e: NullLiteral): Token {
		return StringToken("NULL")
	}

	protected fun macroLiteral(e: MacroLiteral): Token {
		return when (e.value) {
			MacroLiteral.Companion.Value.CURRENT_TIMESTAMP -> StringToken("CURRENT_TIMESTAMP")
		}
	}

	protected fun subQueryLiteral(e: SubQueryLiteral<*>): Token {
		return when (e) {
			is QueryStatement<*> -> genCtx.query().buildToken(e)
			else -> throwUnknownType(e)
		}
	}

}