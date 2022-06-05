package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.QualifiedAllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

fun main() {
	MovieDbMiscTest().all()
}


class MovieDbMiscTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", query1())
	}


	private fun printQuery(name: String, query: QueryStatement?) {
		println("--QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println("$str;")
		} else {
			println("--")
		}
		println()
	}


	private fun query1(): QueryStatement {

		val derived = DerivedTable("result")

		return QueryStatement(
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
	}


}