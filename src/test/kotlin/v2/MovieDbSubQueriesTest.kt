package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AndChainCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.MinAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.NotEqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.NotInSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectDistinctStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement


fun main() {
	MovieDbSubQueriesTest().all()
}


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/subqueries-exercises-on-movie-database.php
 */
class MovieDbSubQueriesTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", query1())
		printQuery("2", query2())
		printQuery("3", query3())
		printQuery("4", query4())
		printQuery("5", query5())
		printQuery("6", query6())
		printQuery("7", query7())
		printQuery("8", query8())
		printQuery("9", query9())
		printQuery("10", query10())
		printQuery("11", query11())
		printQuery("12", query12())
		printQuery("13", query13())
		printQuery("14", query14())
		printQuery("15", query15())
		printQuery("16", query16())

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


	/**
	 * SELECT
	 * FROM actor
	 * WHERE act_id IN(
	 * 		SELECT act_id
	 * 		FROM movie_cast
	 * 		WHERE mov_id IN (
	 * 			SELECT mov_id
	 * 			FROM movie
	 * 			WHERE mov_title='Annie Hall'
	 * 		)
	 * );
	 */
	private fun query1() = QueryStatement(
		select = SelectStatement(
			listOf(
				AllSelectExpression()
			)
		),
		from = FromStatement(
			listOf(
				Actor
			)
		),
		where = WhereStatement(
			InSubQueryCondition(
				Actor.id,
				QueryStatement(
					select = SelectStatement(
						listOf(
							MovieCast.actorId
						)
					),
					from = FromStatement(
						listOf(
							MovieCast
						)
					),
					where = WhereStatement(
						InSubQueryCondition(
							MovieCast.movieId,
							QueryStatement(
								select = SelectStatement(
									listOf(
										Movie.id
									)
								),
								from = FromStatement(
									listOf(
										Movie
									)
								),
								where = WhereStatement(
									EqualCondition(
										Movie.title,
										StringLiteral("Annie Hall")
									)
								)
							)
						)
					)
				)
			)
		)
	)


	/**
	 * SELECT dir_fname, dir_lname
	 * FROM  director
	 * WHERE dir_id in (
	 * 		SELECT dir_id
	 * 		FROM movie_direction
	 * 		WHERE mov_id in(
	 * 			SELECT mov_id
	 * 			FROM movie_cast WHERE role = ANY (
	 * 				SELECT role
	 * 				FROM movie_cast
	 * 				WHERE mov_id IN (
	 * 					SELECT  mov_id
	 * 					FROM movie
	 * 					WHERE mov_title='Eyes Wide Shut'
	 * 				)
	 * 			)
	 * 		)
	 * 	);
	 */
	private fun query2(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 *		mov_title,
	 *		mov_year,
	 *		mov_time,
	 *		mov_dt_rel AS Date_of_Release,
	 *		mov_rel_country AS Releasing_Country
	 * FROM movie
	 * WHERE mov_rel_country<>'UK';
	 */
	private fun query3() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title,
				Movie.year,
				Movie.time,
				Movie.dateRelease.alias("date_of_release"),
				Movie.releaseCountry.alias("releasing_country")
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			NotEqualCondition(
				Movie.releaseCountry,
				StringLiteral("UK")
			)
		)
	)


	/**
	 * SELECT
	 * 		mov_title,
	 * 		mov_year,
	 * 		mov_dt_rel,
	 * 		dir_fname,
	 * 		dir_lname,
	 * 		act_fname,
	 * 		act_lname
	 * FROM
	 * 		movie a,
	 * 		movie_direction b,
	 * 		director c,
	 * 		rating d,
	 * 		reviewer e,
	 * 		actor f,
	 * 		movie_cast g
	 * WHERE a.mov_id=b.mov_id
	 * 		AND b.dir_id = c.dir_id
	 *  	AND a.mov_id = d.mov_id
	 *   	AND d.rev_id = e.rev_id
	 *    	AND a.mov_id = g.mov_id
	 *      AND g.act_id = f.act_id
	 * 	 	AND e.rev_name IS NULL;
	 */
	private fun query4(): QueryStatement {

		val a = Movie.alias("a")
		val b = MovieDirection.alias("b")
		val c = Director.alias("c")
		val d = Rating.alias("d")
		val e = Reviewer.alias("e")
		val f = Actor.alias("f")
		val g = MovieCast.alias("g")

		return QueryStatement(
			select = SelectStatement(
				listOf(
					a.title,
					a.year,
					a.dateRelease,
					c.fName,
					c.lName,
					f.fName,
					f.lName
				)
			),
			from = FromStatement(
				listOf(
					a, b, c, d, e, f, g
				)
			),
			where = WhereStatement(
				AndChainCondition(
					listOf(
						EqualCondition(
							a.id,
							b.movieId
						),
						EqualCondition(
							b.directorId,
							c.id
						),
						EqualCondition(
							a.id,
							d.movieId
						),
						EqualCondition(
							d.reviewerId,
							e.id
						),
						EqualCondition(
							a.id,
							g.movieId
						),
						EqualCondition(
							g.actorId,
							f.id
						),
						IsNullCondition(e.name)
					)
				)
			)
		)
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id=(
	 * 		SELECT mov_id
	 * 		FROM movie_direction
	 * 		WHERE dir_id=(
	 * 			SELECT dir_id
	 * 			FROM director
	 * 			WHERE dir_fname='Woody' AND dir_lname='Allen'
	 * 		)
	 * );
	 */
	private fun query5(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT DISTINCT mov_year
	 * FROM movie
	 * WHERE mov_id IN (
	 * 		SELECT mov_id
	 * 		FROM rating
	 * 		WHERE rev_stars>3
	 * )
	 * ORDER BY mov_year;
	 */
	private fun query6() = QueryStatement(
		select = SelectDistinctStatement(
			listOf(
				Movie.year
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			InSubQueryCondition(
				Movie.id,
				QueryStatement(
					select = SelectStatement(
						listOf(
							Rating.movieId
						)
					),
					from = FromStatement(
						listOf(
							Rating
						)
					),
					where = WhereStatement(
						GreaterThanCondition(
							Rating.stars,
							IntLiteral(3)
						)
					)
				)
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Movie.year, Dir.ASC)
			)
		)
	)


	/**
	 * SELECT DISTINCT mov_title
	 * FROM movie
	 * WHERE mov_id IN (
	 * 		SELECT mov_id
	 * 		FROM movie
	 * 		WHERE mov_id NOT IN (
	 * 			SELECT mov_id FROM Rating
	 * 		)
	 * );
	 */
	private fun query7() = QueryStatement(
		select = SelectDistinctStatement(
			listOf(
				Movie.title
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			InSubQueryCondition(
				Movie.id,
				QueryStatement(
					select = SelectStatement(
						listOf(
							Movie.id
						)
					),
					from = FromStatement(
						listOf(
							Movie
						)
					),
					where = WhereStatement(
						NotInSubQueryCondition(
							Movie.id,
							QueryStatement(
								select = SelectDistinctStatement(
									listOf(
										Rating.movieId
									)
								),
								from = FromStatement(
									listOf(
										Rating
									)
								)
							)
						)
					)
				)
			)
		)
	)


	/**
	 * SELECT DISTINCT rev_name
	 * FROM reviewer
	 * WHERE rev_id IN (
	 * 		SELECT rev_id
	 * 		FROM rating
	 * 		WHERE rev_stars IS NULL
	 * );
	 */
	private fun query8(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 * 		rev_name,
	 * 		mov_title,
	 * 		rev_stars
	 * FROM
	 * 		reviewer,
	 * 		rating,
	 * 		movie
	 * WHERE reviewer.rev_id = rating.rev_id
	 * 		AND movie.mov_id=rating.mov_id
	 *      AND reviewer.rev_name IS NOT NULL
	 *      AND rating.rev_stars IS NOT NULL
	 * ORDER BY rev_name, mov_title, rev_stars;
	 */
	private fun query9(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 * 		rev_name,
	 * 		mov_title
	 * FROM
	 * 		reviewer,
	 * 		movie,
	 * 		rating,
	 * 		rating r2
	 * WHERE rating.mov_id=movie.mov_id
	 *   AND reviewer.rev_id = rating.rev_ID
	 *   AND rating.rev_id = r2.rev_id
	 * GROUP BY rev_name, mov_title
	 * HAVING count(*) > 1;
	 */
	private fun query10(): QueryStatement {
		val r2 = Rating.alias("r2")
		return QueryStatement(
			select = SelectStatement(
				listOf(
					Reviewer.name,
					Movie.title
				)
			),
			from = FromStatement(
				listOf(
					Reviewer,
					Movie,
					Rating,
					r2
				)
			),
			where = WhereStatement(
				AndChainCondition(
					listOf(
						EqualCondition(
							Rating.movieId,
							Movie.id
						),
						EqualCondition(
							Reviewer.id,
							Rating.reviewerId
						),
						EqualCondition(
							Rating.reviewerId,
							r2.reviewerId
						)
					)
				)
			),
			groupBy = GroupByStatement(
				listOf(
					Reviewer.name,
					Movie.title
				)
			),
			having = HavingStatement(
				GreaterThanCondition(
					CountAllAggFunction(),
					IntLiteral(1)
				)
			)
		)
	}


	/**
	 * SELECT mov_title, MAX(rev_stars)
	 * FROM movie, rating
	 * WHERE movie.mov_id=rating.mov_id
	 * 		AND rating.rev_stars IS NOT NULL
	 * GROUP BY mov_title
	 * ORDER BY mov_title;
	 */
	private fun query11() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title,
				MaxAggFunction(Rating.stars)
			)
		),
		from = FromStatement(
			listOf(
				Movie,
				Rating
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					Movie.id,
					Rating.movieId
				),
				IsNotNullCondition(
					Rating.stars
				)
			)
		),
		groupBy = GroupByStatement(
			listOf(
				Movie.title
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Movie.title, Dir.ASC)
			)
		)
	)


	/**
	 * SELECT DISTINCT reviewer.rev_name
	 * FROM reviewer, rating, movie
	 * WHERE reviewer.rev_id = rating.rev_id
	 * 		AND movie.mov_id = rating.mov_id
	 * 		AND movie.mov_title = 'American Beauty';
	 */
	private fun query12(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT movie.mov_title
	 * FROM movie
	 * WHERE movie.mov_id IN(
	 * 		SELECT mov_id
	 * 		FROM rating
	 * 		WHERE rev_id NOT IN (
	 * 			SELECT rev_id
	 * 			FROM reviewer
	 * 			WHERE rev_name='Paul Monks'
	 * 		)
	 * );
	 */
	private fun query13(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 * 		reviewer.rev_name,
	 * 		movie.mov_title,
	 * 		rating.rev_stars
	 * FROM reviewer, movie, rating
	 * WHERE rating.rev_stars = (
	 * 			SELECT MIN(rating.rev_stars)
	 * 			FROM rating
	 * 		)
	 * 		AND rating.rev_id = reviewer.rev_id
	 * 		AND rating.mov_id = movie.mov_id;
	 */
	private fun query14() = QueryStatement(
		select = SelectStatement(
			listOf(
				Reviewer.name,
				Movie.title,
				Rating.stars
			)
		),
		from = FromStatement(
			listOf(
				Reviewer,
				Movie,
				Rating
			)
		),
		where = WhereStatement(
			AndChainCondition(
				listOf(
					EqualCondition(
						Rating.stars,
						QueryStatement(
							select = SelectStatement(
								listOf(
									MinAggFunction(Rating.stars)
								)
							),
							from = FromStatement(
								listOf(
									Rating
								)
							)
						)
					),
					EqualCondition(
						Rating.reviewerId,
						Reviewer.id
					),
					EqualCondition(
						Rating.movieId,
						Movie.id
					)
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * 		JOIN  movie_direction
	 *  	ON movie.mov_id = movie_direction.mov_id
	 * 			JOIN  director
	 *  		ON movie_direction.dir_id = director.dir_id
	 * WHERE dir_fname = 'James' AND dir_lname='Cameron';
	 */
	private fun query15() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title
			)
		),
		from = FromStatement(
			listOf(
				JoinClause(
					JoinOp.LEFT,
					JoinClause(
						JoinOp.LEFT,
						Movie,
						MovieDirection,
						ConditionJoinConstraint(
							EqualCondition(
								Movie.id,
								MovieDirection.movieId
							)
						)
					),
					Director,
					ConditionJoinConstraint(
						EqualCondition(
							MovieDirection.directorId,
							Director.id
						)
					)
				)
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					Director.fName,
					StringLiteral("James")
				),
				EqualCondition(
					Director.lName,
					StringLiteral("Cameron")
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id IN (
	 * 		SELECT mov_id
	 * 		FROM movie_cast
	 * 		WHERE act_id IN (
	 * 			SELECT act_id
	 * 			FROM actor
	 * 			WHERE act_id IN (
	 * 				SELECT act_id
	 * 				FROM movie_cast
	 * 				GROUP BY act_id
	 * 				HAVING COUNT(act_id)>1
	 * 			)
	 * 		)
	 * 	);
	 */
	private fun query16(): QueryStatement? {
		// TODO
		return null
	}

}