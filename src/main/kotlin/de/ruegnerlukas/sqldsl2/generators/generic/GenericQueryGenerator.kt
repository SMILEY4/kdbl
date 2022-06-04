package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.QueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.limit.LimitStatement
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl2.tokens.CsvListToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericQueryGenerator(private val genCtx: GeneratorContext) : QueryGenerator, GenericGeneratorBase<QueryStatement>() {

	override fun buildToken(e: QueryStatement): Token {
		return ListToken()
			.add(select(e.select))
			.add(from(e.from))
			.addIf(e.where != null) { where(e.where!!) }
			.addIf(e.groupBy != null) { groupBy(e.groupBy!!) }
			.addIf(e.having != null) { having(e.having!!) }
			.addIf(e.orderBy != null) { orderBy(e.orderBy!!) }
			.addIf(e.limit != null) { limit(e.limit!!) }
	}

	protected fun select(statement: SelectStatement): Token {
		return ListToken()
			.add("SELECT")
			.addIf(statement.distinct, "DISTINCT")
			.add(CsvListToken(statement.expressions.map { genCtx.select().buildToken(it) }))
	}

	protected fun from(statement: FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(CsvListToken(statement.expressions.map { genCtx.from().buildToken(it) }))
	}

	protected fun where(statement: WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(genCtx.where().buildToken(statement.expression))
	}

	protected fun groupBy(statement: GroupByStatement): Token {
		return ListToken()
			.add("GROUP BY")
			.add(CsvListToken(statement.expressions.map { genCtx.groupBy().buildToken(it) }))
	}

	protected fun having(statement: HavingStatement): Token {
		return ListToken()
			.add("HAVING")
			.add(genCtx.having().buildToken(statement.expression))
	}

	protected fun orderBy(statement: OrderByStatement): Token {
		return ListToken()
			.add("ORDER BY")
			.add(CsvListToken(statement.expressions.map { genCtx.orderBy().buildToken(it) }))
	}

	protected fun limit(statement: LimitStatement): Token {
		return ListToken()
			.add("LIMIT")
			.add(statement.limit.toString())
			.addIf(statement.offset != null, "OFFSET")
			.addIf(statement.offset != null, statement.offset!!.toString())
	}

}