package sqldsl

import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.QualifiedAllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MovieDbMiscTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryStatement<*>, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun query1() {

		val derived = DerivedTable("result")

		val query = QueryStatement<AnyValueType>(
			select = SelectStatement(
				listOf(
					AllSelectExpression(),
					derived.column(Actor.gender),
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
					derived.column(Actor.gender),
					StringLiteral("f")
				)
			)
		)
		assertQuery(
			query,
			"SELECT *, result.act_gender, result.* FROM movie, movie AS my_movies, actor JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id)), (actor JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id))) AS result WHERE (result.act_gender) = ('f')"
		)
	}


}