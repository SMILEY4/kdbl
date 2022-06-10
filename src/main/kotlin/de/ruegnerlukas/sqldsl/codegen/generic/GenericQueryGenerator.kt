package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.QueryGenerator
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericQueryGenerator(private val genCtx: GeneratorContext) : QueryGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>): Token {
		return ListToken()
			.add(select(e.select))
			.add(from(e.from))
			.addIf(e.where != null) { where(e.where!!) }
			.addIf(e.groupBy != null) { groupBy(e.groupBy!!) }
			.addIf(e.having != null) { having(e.having!!) }
			.addIf(e.orderBy != null) { orderBy(e.orderBy!!) }
			.addIf(e.limit != null) { limit(e.limit!!) }
	}

	protected fun select(statement: de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement): Token {
		return ListToken()
			.add("SELECT")
			.addIf(statement.distinct, "DISTINCT")
			.add(CsvListToken(statement.expressions.map { genCtx.select().buildToken(it) }))
	}

	protected fun from(statement: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(CsvListToken(statement.expressions.map { genCtx.from().buildToken(it) }))
	}

	protected fun where(statement: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(genCtx.where().buildToken(statement.expression))
	}

	protected fun groupBy(statement: de.ruegnerlukas.sqldsl.dsl.grammar.groupby.GroupByStatement): Token {
		return ListToken()
			.add("GROUP BY")
			.add(CsvListToken(statement.expressions.map { genCtx.groupBy().buildToken(it) }))
	}

	protected fun having(statement: de.ruegnerlukas.sqldsl.dsl.grammar.having.HavingStatement): Token {
		return ListToken()
			.add("HAVING")
			.add(genCtx.having().buildToken(statement.expression))
	}

	protected fun orderBy(statement: de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByStatement): Token {
		return ListToken()
			.add("ORDER BY")
			.add(CsvListToken(statement.expressions.map { genCtx.orderBy().buildToken(it) }))
	}

	protected fun limit(statement: de.ruegnerlukas.sqldsl.dsl.grammar.limit.LimitStatement): Token {
		return ListToken()
			.add("LIMIT")
			.add(statement.limit.toString())
			.addIf(statement.offset != null, "OFFSET")
			.addIf(statement.offset != null, statement.offset!!.toString())
	}

}