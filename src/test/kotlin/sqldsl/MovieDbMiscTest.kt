package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.QualifiedAllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MovieDbMiscTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryStatement, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun query1() {

		val derived = DerivedTable("result")

		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					AllSelectExpression(),
					derived.columnInt(Actor.gender),
					QualifiedAllSelectExpression(derived)
				)
			),
			from = FromStatement(
				listOf(
					Movie,
					Movie.alias("my_movies"),
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
					),
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

		assertQuery(query, "SELECT *, result.act_gender, result.* FROM movie, movie AS my_movies, actor INNER JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id)), (actor INNER JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id))) AS result WHERE (result.act_gender) = ('f')")
	}


}