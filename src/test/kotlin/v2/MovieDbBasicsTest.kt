package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InListCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.LessThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.LikeCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.ListLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.NotCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/basic-exercises-on-movie-database.php
 */
class MovieDbBasicsTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryStatement, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	/**
	 * SELECT mov_title, mov_year
	 * FROM movie;
	 */
	@Test
	fun query1() {
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Movie.title,
					Movie.year
				)
			),
			from = FromStatement(
				listOf(
					Movie
				)
			)
		)
		assertQuery(query, "SELECT movie.mov_title, movie.mov_year FROM movie")
	}


	/**
	 * SELECT mov_year
	 * FROM movie
	 * WHERE mov_title='American Beauty';
	 */
	@Test
	fun query2() {
		val query = QueryStatement(
			select = SelectStatement(
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
				EqualCondition(Movie.title, StringLiteral("American Beauty"))
			)
		)
		assertQuery(query, "SELECT movie.mov_year FROM movie WHERE (movie.mov_title) = ('American Beauty')")
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_year=1999;
	 */
	@Test
	fun query3() {
		val query = QueryStatement(
			select = SelectStatement(
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
				EqualCondition(Movie.year, IntLiteral(1999))
			)
		)
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE (movie.mov_year) = (1999)")
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_year<1998;
	 */
	@Test
	fun query4() {
		val query = QueryStatement(
			select = SelectStatement(
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
				LessThanCondition(Movie.year, IntLiteral(1999))
			)
		)
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE (movie.mov_year) < (1999)")
	}


	/**
	 * SELECT reviewer.rev_name
	 * FROM reviewer, rating
	 * WHERE rating.rev_id = reviewer.rev_id
	 * AND rating.rev_stars>=7
	 * AND reviewer.rev_name IS NOT NULL;
	 */
	@Test
	fun query6() {
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Reviewer.name
				)
			),
			from = FromStatement(
				listOf(
					Reviewer,
					Rating
				)
			),
			where = WhereStatement(
				AndCondition(
					EqualCondition(
						Rating.reviewerId,
						Reviewer.id
					),
					AndCondition(
						GreaterOrEqualThanCondition(
							Rating.stars,
							IntLiteral(7)
						),
						IsNotNullCondition(
							Reviewer.name
						)
					)
				)
			)
		)
		assertQuery(query, "SELECT reviewer.rev_name FROM reviewer, rating WHERE ((rating.rev_id) = (reviewer.rev_id)) AND (((rating.rev_stars) >= (7)) AND ((reviewer.rev_name) IS NOT NULL))")
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id NOT IN (SELECT mov_id FROM rating);
	 */
	@Test
	fun query7() {
		val query = QueryStatement(
			select = SelectStatement(
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
				NotCondition(
					InSubQueryCondition(
						Movie.id,
						QueryStatement(
							SelectStatement(
								listOf(
									Rating.movieId
								)
							),
							FromStatement(
								listOf(
									Rating
								)
							)
						)
					)
				)
			)
		)
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE NOT ((movie.mov_id) IN (SELECT rating.mov_id FROM rating))")
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id in (905, 907, 917);
	 */
	@Test
	fun query8() {
		val query = QueryStatement(
			select = SelectStatement(
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
				InListCondition(
					Movie.id,
					ListLiteral(
						listOf(
							IntLiteral(905),
							IntLiteral(907),
							IntLiteral(917)
						)
					)
				)
			)
		)
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE (movie.mov_id) IN (905, 907, 917)")
	}


	/**
	 * SELECT mov_id, mov_title, mov_year
	 * FROM movie
	 * WHERE mov_title LIKE '%Boogie%Nights%'
	 * ORDER BY mov_year ASC;
	 */
	@Test
	fun query9() {
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Movie.id,
					Movie.title,
					Movie.year
				)
			),
			from = FromStatement(
				listOf(
					Movie
				)
			),
			where = WhereStatement(
				LikeCondition(
					Movie.title,
					"%Boogie%Nights%"
				)
			),
			orderBy = OrderByStatement(
				listOf(
					OrderByExpression(Movie.year, Dir.ASC)
				)
			)
		)
		assertQuery(query, "SELECT movie.mov_id, movie.mov_title, movie.mov_year FROM movie WHERE (movie.mov_title) LIKE '%Boogie%Nights%' ORDER BY (movie.mov_year) ASC")
	}


	/**
	 * SELECT act_id
	 * FROM actor
	 * WHERE act_fname='Woody'
	 * AND act_lname='Allen';
	 */
	@Test
	fun query10() {
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Actor.id
				)
			),
			from = FromStatement(
				listOf(
					Actor
				)
			),
			where = WhereStatement(
				AndCondition(
					EqualCondition(
						Actor.fName,
						StringLiteral("Woddy")
					),
					EqualCondition(
						Actor.lName,
						StringLiteral("Allen")
					)
				)
			),
		)
		assertQuery(query, "SELECT actor.act_id FROM actor WHERE ((actor.act_fname) = ('Woddy')) AND ((actor.act_lname) = ('Allen'))")
	}

}



















