package sqldsl

import de.ruegnerlukas.sqldsl.builders.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.builders.SQL
import de.ruegnerlukas.sqldsl.builders.and
import de.ruegnerlukas.sqldsl.builders.asc
import de.ruegnerlukas.sqldsl.builders.countAll
import de.ruegnerlukas.sqldsl.builders.isEqual
import de.ruegnerlukas.sqldsl.builders.isGreaterThan
import de.ruegnerlukas.sqldsl.builders.isIn
import de.ruegnerlukas.sqldsl.builders.isNotEqual
import de.ruegnerlukas.sqldsl.builders.isNotIn
import de.ruegnerlukas.sqldsl.builders.isNotNull
import de.ruegnerlukas.sqldsl.builders.isNull
import de.ruegnerlukas.sqldsl.builders.join
import de.ruegnerlukas.sqldsl.builders.max
import de.ruegnerlukas.sqldsl.builders.min
import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericQueryGenerator
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/subqueries-exercises-on-movie-database.php
 */
class MovieDbSubQueriesTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryBuilderEndStep<*>, expected: String) {
		val strQuery = generator.buildString(query.build())
		println(strQuery)
		assertEquals(expected, strQuery)
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
	@Test
	fun query1() {
		val query = SQL
			.selectAll()
			.from(Actor)
			.where(
				Actor.id.isIn(
					SQL
						.select(MovieCast.actorId)
						.from(MovieCast)
						.where(
							MovieCast.movieId.isIn(
								SQL
									.select(Movie.id)
									.from(Movie)
									.where(Movie.title.isEqual("Annie Hall"))
							)
						)
				)
			)
		assertQuery(
			query,
			"SELECT * FROM actor WHERE (actor.act_id) IN (SELECT movie_cast.act_id FROM movie_cast WHERE (movie_cast.mov_id) IN (SELECT movie.mov_id FROM movie WHERE (movie.mov_title) = ('Annie Hall')))"
		)
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
	@Test
	fun query3() {
		val query = SQL
			.select(
				Movie.title,
				Movie.year,
				Movie.time,
				Movie.dateRelease.alias("date_of_release"),
				Movie.releaseCountry.alias("releasing_country")
			)
			.from(Movie)
			.where(Movie.releaseCountry.isNotEqual("UK"))
		assertQuery(
			query,
			"SELECT movie.mov_title, movie.mov_year, movie.mov_time, (movie.mov_dt_rel) AS date_of_release, (movie.mov_rel_country) AS releasing_country FROM movie WHERE (movie.mov_rel_country) != ('UK')"
		)
	}


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
	@Test
	fun query4() {
		val a = Movie.alias("a")
		val b = MovieDirection.alias("b")
		val c = Director.alias("c")
		val d = Rating.alias("d")
		val e = Reviewer.alias("e")
		val f = Actor.alias("f")
		val g = MovieCast.alias("g")
		val query = SQL
			.select(a.title, a.year, a.dateRelease, c.fName, c.lName, f.fName, f.lName)
			.from(a, b, c, d, e, f, g)
			.where(
				and(
					a.id.isEqual(b.movieId),
					b.directorId.isEqual(c.id),
					a.id.isEqual(d.movieId),
					d.reviewerId.isEqual(e.id),
					a.id.isEqual(g.movieId),
					g.actorId.isEqual(f.id),
					e.name.isNull()
				)
			)
		assertQuery(
			query,
			"SELECT a.mov_title, a.mov_year, a.mov_dt_rel, c.dir_fname, c.dir_lname, f.act_fname, f.act_lname FROM movie AS a, movie_direction AS b, director AS c, rating AS d, reviewer AS e, actor AS f, movie_cast AS g WHERE ((a.mov_id) = (b.mov_id)) AND ((b.dir_id) = (c.dir_id)) AND ((a.mov_id) = (d.mov_id)) AND ((d.rev_id) = (e.rev_id)) AND ((a.mov_id) = (g.mov_id)) AND ((g.act_id) = (f.act_id)) AND ((e.rev_name) IS NULL)"
		)
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
	private fun query6() {
		val query = SQL
			.select(Movie.year)
			.from(Movie)
			.where(
				Movie.id.isIn(
					SQL
						.select(Rating.movieId)
						.from(Rating)
						.where(Rating.stars.isGreaterThan(3))
				)
			)
			.orderBy(Movie.year.asc())
		assertQuery(
			query,
			"SELECT DISTINCT movie.mov_year FROM movie WHERE (movie.mov_id) IN (SELECT rating.mov_id FROM rating WHERE (rating.rev_stars) > (3)) ORDER BY (movie.mov_year) ASC"
		)
	}


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
	@Test
	fun query7() {
		val query = SQL
			.selectDistinct(Movie.title)
			.from(Movie)
			.where(
				Movie.id.isIn(
					SQL
						.select(Movie.id)
						.from(Movie)
						.where(
							Movie.id.isNotIn(
								SQL
									.selectDistinct(Rating.movieId)
									.from(Rating)
							)
						)
				)
			)

		assertQuery(
			query,
			"SELECT DISTINCT movie.mov_title FROM movie WHERE (movie.mov_id) IN (SELECT movie.mov_id FROM movie WHERE (movie.mov_id) NOT IN (SELECT DISTINCT rating.mov_id FROM rating))"
		)
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
	@Test
	fun query10() {
		val r2 = Rating.alias("r2")
		val query = SQL
			.select(Reviewer.name, Movie.title)
			.from(Reviewer, Movie, Rating, r2)
			.where(Rating.movieId.isEqual(Movie.id) and Reviewer.id.isEqual(Rating.reviewerId) and Rating.reviewerId.isEqual(r2.reviewerId))
			.groupBy(Reviewer.name, Movie.title)
			.having(countAll().isGreaterThan(1))
		assertQuery(
			query,
			"SELECT reviewer.rev_name, movie.mov_title FROM reviewer, movie, rating, rating AS r2 WHERE (((rating.mov_id) = (movie.mov_id)) AND ((reviewer.rev_id) = (rating.rev_id))) AND ((rating.rev_id) = (r2.rev_id)) GROUP BY reviewer.rev_name, movie.mov_title HAVING (COUNT(*)) > (1)"
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
	@Test
	fun query11() {
		val query = SQL
			.select(Movie.title, Rating.stars.max())
			.from(Movie, Rating)
			.where(Movie.id.isEqual(Rating.movieId) and Rating.stars.isNotNull())
			.groupBy(Movie.title)
			.orderBy(Movie.title.asc())
		assertQuery(
			query,
			"SELECT movie.mov_title, MAX(rating.rev_stars) FROM movie, rating WHERE ((movie.mov_id) = (rating.mov_id)) AND ((rating.rev_stars) IS NOT NULL) GROUP BY movie.mov_title ORDER BY (movie.mov_title) ASC"
		)
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
	@Test
	fun query14() {
		val query = SQL
			.select(Reviewer.name, Movie.title, Rating.stars)
			.from(Reviewer, Movie, Rating)
			.where(
				Rating.stars.isEqual(SQL.select(Rating.stars.min()).from(Rating))
						and Rating.reviewerId.isEqual(Reviewer.id)
						and Rating.movieId.isEqual(Movie.id)
			)
		assertQuery(
			query,
			"SELECT reviewer.rev_name, movie.mov_title, rating.rev_stars FROM reviewer, movie, rating WHERE (((rating.rev_stars) = (SELECT MIN(rating.rev_stars) FROM rating)) AND ((rating.rev_id) = (reviewer.rev_id))) AND ((rating.mov_id) = (movie.mov_id))"
		)
	}


	/**
	 * SELECT mov_title
	 * FROM movie
	 * 		JOIN  movie_direction
	 *  	ON movie.mov_id = movie_direction.mov_id
	 * 			JOIN  director
	 *  		ON movie_direction.dir_id = director.dir_id
	 * WHERE dir_fname = 'James' AND dir_lname='Cameron';
	 */
	@Test
	fun query15() {
		val query = SQL
			.select(Movie.title)
			.from(
				Movie
					.join(MovieDirection).on(Movie.id.isEqual(MovieDirection.movieId))
					.join(Director).on(MovieDirection.directorId.isEqual(Director.id))
			)
			.where(Director.fName.isEqual("James") and Director.lName.isEqual("Cameron"))
		assertQuery(
			query,
			"SELECT movie.mov_title FROM (movie JOIN movie_direction ON ((movie.mov_id) = (movie_direction.mov_id))) JOIN director ON ((movie_direction.dir_id) = (director.dir_id)) WHERE ((director.dir_fname) = ('James')) AND ((director.dir_lname) = ('Cameron'))"
		)
	}

}