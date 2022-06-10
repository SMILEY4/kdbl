package sqldsl

import de.ruegnerlukas.sqldsl.dsl.builders.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.builders.SQL
import de.ruegnerlukas.sqldsl.dsl.builders.and
import de.ruegnerlukas.sqldsl.dsl.builders.isEqual
import de.ruegnerlukas.sqldsl.dsl.builders.isGreaterOrEqualThan
import de.ruegnerlukas.sqldsl.dsl.builders.isIn
import de.ruegnerlukas.sqldsl.dsl.builders.isLike
import de.ruegnerlukas.sqldsl.dsl.builders.isNotIn
import de.ruegnerlukas.sqldsl.dsl.builders.isNotNull
import de.ruegnerlukas.sqldsl.codegen.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.codegen.generic.GenericQueryGenerator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/basic-exercises-on-movie-database.php
 */
class MovieDbBasicsTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryBuilderEndStep<*>, expected: String) {
		val strQuery = generator.buildString(query.build())
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	/**
	 * SELECT mov_title, mov_year
	 * FROM movie;
	 */
	@Test
	fun query1() {
		val query = SQL
			.select(Movie.title, Movie.year)
			.from(Movie)
		assertQuery(query, "SELECT movie.mov_title, movie.mov_year FROM movie")
	}


	/**
	 * SELECT mov_year
	 * FROM movie
	 * WHERE mov_title='American Beauty';
	 */
	@Test
	fun query2() {
		val query = SQL
			.select(Movie.year)
			.from(Movie)
			.where(Movie.title.isEqual("American Beauty"))
		assertQuery(query, "SELECT movie.mov_year FROM movie WHERE (movie.mov_title) = ('American Beauty')")
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_year=1999;
	 */
	@Test
	fun query3() {
		val query = SQL
			.select(Movie.title)
			.from(Movie)
			.where(Movie.year.isEqual(1999))
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE (movie.mov_year) = (1999)")
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
		val query = SQL
			.select(Reviewer.name)
			.from(Reviewer, Rating)
			.where(
				Rating.reviewerId.isEqual(Reviewer.id)
						and Rating.stars.isGreaterOrEqualThan(7)
						and Reviewer.name.isNotNull()
			)
		assertQuery(
			query,
			"SELECT reviewer.rev_name FROM reviewer, rating WHERE (((rating.rev_id) = (reviewer.rev_id)) AND ((rating.rev_stars) >= (7))) AND ((reviewer.rev_name) IS NOT NULL)"
		)
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id NOT IN (SELECT mov_id FROM rating);
	 */
	@Test
	fun query7() {
		val query = SQL
			.select(Movie.title)
			.from(Movie)
			.where(Movie.id.isNotIn(SQL.select(Rating.movieId).from(Rating)))
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE (movie.mov_id) NOT IN (SELECT rating.mov_id FROM rating)")
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id in (905, 907, 917);
	 */
	@Test
	fun query8() {
		val query = SQL
			.select(Movie.title)
			.from(Movie)
			.where(Movie.id.isIn(905, 907, 917))
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
		val query = SQL
			.select(Movie.id, Movie.title, Movie.year)
			.from(Movie)
			.where(Movie.title.isLike("%Boogie%Nights%"))
			.orderBy(
				de.ruegnerlukas.sqldsl.dsl.grammar.orderby.OrderByExpression(
					Movie.year,
					de.ruegnerlukas.sqldsl.dsl.grammar.orderby.Dir.ASC
				)
			)
		assertQuery(
			query,
			"SELECT movie.mov_id, movie.mov_title, movie.mov_year FROM movie WHERE (movie.mov_title) LIKE '%Boogie%Nights%' ORDER BY (movie.mov_year) ASC"
		)
	}


	/**
	 * SELECT act_id
	 * FROM actor
	 * WHERE act_fname='Woody'
	 * AND act_lname='Allen';
	 */
	@Test
	fun query10() {
		val query = SQL
			.select(Actor.id)
			.from(Actor)
			.where(Actor.fName.isEqual("Woddy") and Actor.lName.isEqual("Allen"))
		assertQuery(query, "SELECT actor.act_id FROM actor WHERE ((actor.act_fname) = ('Woddy')) AND ((actor.act_lname) = ('Allen'))")
	}

}
