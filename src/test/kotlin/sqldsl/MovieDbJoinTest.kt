package sqldsl

import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/joins-exercises-on-movie-database.php
 */
class MovieDbJoinTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())


	private fun assertQuery(query: QueryStatement, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}



	/**
	 * SELECT rev_name
	 * FROM reviewer
	 * 		INNER JOIN rating USING(rev_id)
	 * WHERE rev_stars IS NULL;
	 */
	@Test
	fun query1() {
		val derived = DerivedTable("result")
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					derived.columnInt(Reviewer.name)
				)
			),
			from = FromStatement(
				listOf(
					derived.assign(
						JoinClause(
							JoinOp.INNER,
							Reviewer,
							Rating,
							UsingJoinConstraint(
								listOf(
									derived.columnInt(Reviewer.id)
								)
							)
						)
					)
				)
			),
			where = WhereStatement(
				IsNullCondition(
					derived.columnInt(Rating.stars)
				)
			)
		)
		assertQuery(query, "SELECT result.rev_name FROM (reviewer INNER JOIN rating USING result.rev_id) AS result WHERE (result.rev_stars) IS NULL")
	}


	@Test
	fun queryTest() {
		val derived = DerivedTable("result")
		val query =  QueryStatement(
			select = SelectStatement(
				listOf(
					AllSelectExpression()
				)
			),
			from = FromStatement(
				listOf(
					derived.assign(
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
			),
			where = WhereStatement(
				EqualCondition(
					derived.columnInt(Actor.gender),
					StringLiteral("f")
				)
			)
		)
		assertQuery(query, "SELECT * FROM (actor INNER JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id))) AS result WHERE (result.act_gender) = ('f')")
	}


}