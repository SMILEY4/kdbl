package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.AggregateFunctionGenerator
import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.grammar.expr.AggregateFunction
import de.ruegnerlukas.sqldsl.grammar.expr.AvgAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.CountAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.MinAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl.tokens.NamedGroupToken
import de.ruegnerlukas.sqldsl.tokens.StringToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericAggregateFunctionGenerator(private val genCtx: GeneratorContext) : AggregateFunctionGenerator, GenericGeneratorBase<AggregateFunction<*>>() {

	override fun buildToken(e: AggregateFunction<*>): Token {
		return when (e) {
			is CountAllAggFunction -> countAll(e)
			is CountAggFunction<*> -> count(e)
			is MaxAggFunction -> max(e)
			is MinAggFunction -> min(e)
			is SumAggFunction -> sum(e)
			is AvgAggFunction -> avg(e)
			else -> throwUnknownType(e)
		}
	}

	protected fun countAll(e: CountAllAggFunction): Token {
		return StringToken("COUNT(*)")
	}

	protected fun count(e: CountAggFunction<*>): Token {
		return NamedGroupToken("COUNT", genCtx.expr().buildToken(e.column))
	}

	protected fun max(e: MaxAggFunction<*>): Token {
		return NamedGroupToken("MAX", genCtx.expr().buildToken(e.expression))
	}

	protected fun min(e: MinAggFunction<*>): Token {
		return NamedGroupToken("MIN", genCtx.expr().buildToken(e.expression))
	}

	protected fun sum(e: SumAggFunction<*>): Token {
		return NamedGroupToken("SUM", genCtx.expr().buildToken(e.expression))
	}

	protected fun avg(e: AvgAggFunction<*>): Token {
		return NamedGroupToken("AVG", genCtx.expr().buildToken(e.expression))
	}

}