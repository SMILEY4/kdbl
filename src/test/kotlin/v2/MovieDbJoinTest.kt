package v2

import de.ruegnerlukas.sqldsl2.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.anyTable
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.join.UsingJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/joins-exercises-on-movie-database.php
 */
class MovieDbJoinTest {

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


}