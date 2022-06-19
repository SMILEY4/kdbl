package kdbl.query

import de.ruegnerlukas.kdbl.builder.SQL
import de.ruegnerlukas.kdbl.builder.and
import de.ruegnerlukas.kdbl.builder.asc
import de.ruegnerlukas.kdbl.builder.isEqual
import de.ruegnerlukas.kdbl.builder.isGreaterOrEqualThan
import de.ruegnerlukas.kdbl.builder.isIn
import de.ruegnerlukas.kdbl.builder.isLike
import de.ruegnerlukas.kdbl.builder.isNotIn
import de.ruegnerlukas.kdbl.builder.isNotNull
import org.junit.jupiter.api.Test
import kdbl.Actor
import kdbl.Movie
import kdbl.Rating
import kdbl.Reviewer
import kdbl.utils.assertQuery


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/basic-exercises-on-movie-database.php
 */
class MovieDbBasicsTest {

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
		assertQuery(query, "SELECT movie.mov_year FROM movie WHERE movie.mov_title = 'American Beauty'")
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
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE movie.mov_year = 1999")
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
			"SELECT reviewer.rev_name FROM reviewer, rating WHERE ((rating.rev_id = reviewer.rev_id) AND (rating.rev_stars >= 7)) AND (reviewer.rev_name IS NOT NULL)"
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
			.where(Movie.id.isNotIn(SQL.select(Rating.movieId).from(Rating).build()))
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE movie.mov_id NOT IN (SELECT rating.mov_id FROM rating)")
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
		assertQuery(query, "SELECT movie.mov_title FROM movie WHERE movie.mov_id IN (905, 907, 917)")
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
			.orderBy(Movie.year.asc())
		assertQuery(
			query,
			"SELECT movie.mov_id, movie.mov_title, movie.mov_year FROM movie WHERE movie.mov_title LIKE '%Boogie%Nights%' ORDER BY movie.mov_year ASC"
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
		assertQuery(query, "SELECT actor.act_id FROM actor WHERE (actor.act_fname = 'Woddy') AND (actor.act_lname = 'Allen')")
	}

}
