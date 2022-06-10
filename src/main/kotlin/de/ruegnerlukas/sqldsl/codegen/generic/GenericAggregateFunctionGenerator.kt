package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.dsl.grammar.expr.AggregateFunction
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.AvgAggFunction
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.CountAggFunction
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.MinAggFunction
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl.codegen.AggregateFunctionGenerator
import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.tokens.NamedGroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericAggregateFunctionGenerator(private val genCtx: GeneratorContext) : AggregateFunctionGenerator,
	GenericGeneratorBase<AggregateFunction<*>>() {

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

	open fun countAll(e: CountAllAggFunction): Token {
		return StringToken("COUNT(*)")
	}

	open fun count(e: CountAggFunction<*>): Token {
		return NamedGroupToken("COUNT", genCtx.expr().buildToken(e.column))
	}

	open fun max(e: MaxAggFunction<*>): Token {
		return NamedGroupToken("MAX", genCtx.expr().buildToken(e.expression))
	}

	open fun min(e: MinAggFunction<*>): Token {
		return NamedGroupToken("MIN", genCtx.expr().buildToken(e.expression))
	}

	open fun sum(e: SumAggFunction<*>): Token {
		return NamedGroupToken("SUM", genCtx.expr().buildToken(e.expression))
	}

	open fun avg(e: AvgAggFunction<*>): Token {
		return NamedGroupToken("AVG", genCtx.expr().buildToken(e.expression))
	}

}