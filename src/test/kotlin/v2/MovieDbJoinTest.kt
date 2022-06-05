package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.anyTable
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

fun main() {
	MovieDbJoinTest().all()
}

/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/joins-exercises-on-movie-database.php
 */
class MovieDbJoinTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", query1())
		printQuery("test", queryTest())
	}


	private fun printQuery(name: String, query: QueryStatement?) {
		println("QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println(str)
		} else {
			println("-")
		}
		println()
	}

	/**
	 * SELECT rev_name
	 * FROM reviewer
	 * INNER JOIN rating USING(rev_id)
	 * WHERE rev_stars IS NULL;
	 */
	fun query1() = QueryStatement(
		select = SelectStatement(
			listOf(
				Reviewer.name.anyTable()
			)
		),
		from = FromStatement(
			listOf(
				JoinClause(
					JoinOp.INNER,
					Reviewer,
					Rating,
					UsingJoinConstraint(
						listOf(
							Reviewer.id.anyTable()
						)
					)
				)
			)
		),
		where = WhereStatement(
			IsNullCondition(
				Rating.stars.anyTable()
			)
		)
	)


	fun queryTest(): QueryStatement {

		return QueryStatement(
			select = SelectStatement(
				listOf(
					AllSelectExpression()
				)
			),
			from = FromStatement(
				listOf(
					JoinClause(
						JoinOp.INNER,
						Actor,
						MovieCast,
						ConditionJoinConstraint(
							EqualCondition(
								Actor.id,
								MovieCast.actorId
							)
						)
					)
				)
			)
		)
	}


}