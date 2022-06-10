package sqldsl

import de.ruegnerlukas.sqldsl.codegen.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.codegen.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MovieDbMiscTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*>, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun query1() {

		val derived = de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable("result")

		val query = de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<AnyValueType>(
			select = de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(
				listOf(
					de.ruegnerlukas.sqldsl.dsl.grammar.select.AllSelectExpression(),
					derived.column(Actor.gender),
					de.ruegnerlukas.sqldsl.dsl.grammar.select.QualifiedAllSelectExpression(derived)
				)
			),
			from = de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement(
				listOf(
					Movie,
					Movie.alias("my_movies"),
					de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause(
						de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER,
						Actor,
						MovieCast,
						de.ruegnerlukas.sqldsl.dsl.grammar.join.ConditionJoinConstraint(
							de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(
								Actor.id,
								MovieCast.actorId
							)
						)
					),
					derived.assign(
						de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinClause(
							de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinOp.INNER,
							Actor,
							MovieCast,
							de.ruegnerlukas.sqldsl.dsl.grammar.join.ConditionJoinConstraint(
								de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(
									Actor.id,
									MovieCast.actorId
								)
							)
						)
					)
				)
			),
			where = de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement(
				de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(
					derived.column(Actor.gender),
					de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("f")
				)
			)
		)
		assertQuery(
			query,
			"SELECT *, result.act_gender, result.* FROM movie, movie AS my_movies, actor JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id)), (actor JOIN movie_cast ON ((actor.act_id) = (movie_cast.act_id))) AS result WHERE (result.act_gender) = ('f')"
		)
	}


}