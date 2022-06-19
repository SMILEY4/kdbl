package sqldsl.query

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.builder.assign
import de.ruegnerlukas.sqldsl.builder.isEqual
import de.ruegnerlukas.sqldsl.builder.isNull
import de.ruegnerlukas.sqldsl.builder.join
import de.ruegnerlukas.sqldsl.dsl.expression.DerivedTable
import org.junit.jupiter.api.Test
import sqldsl.Actor
import sqldsl.MovieCast
import sqldsl.Rating
import sqldsl.Reviewer
import sqldsl.utils.assertQuery


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/joins-exercises-on-movie-database.php
 */
class MovieDbJoinTest {

	/**
	 * SELECT rev_name
	 * FROM reviewer
	 * 		INNER JOIN rating USING(rev_id)
	 * WHERE rev_stars IS NULL;
	 */
	@Test
	fun query1() {
		val derived = DerivedTable("result")
		val query = SQL
			.select(derived.column(Reviewer.name))
			.from(
				Reviewer.join(Rating)
					.using(derived.column(Reviewer.id))
					.assign(derived)
			)
			.where(derived.column(Rating.stars).isNull())
		assertQuery(
			query, "SELECT result.rev_name FROM (reviewer INNER JOIN rating USING (result.rev_id)) AS result WHERE result.rev_stars IS NULL"
		)
	}


	@Test
	fun queryTest() {
		val derived = DerivedTable("result")
		val query = SQL
			.selectAll()
			.from(
				Actor.join(MovieCast)
					.on(Actor.id.isEqual(MovieCast.actorId))
					.assign(derived)
			)
			.where(derived.column(Actor.gender).isEqual("F"))
		assertQuery(
			query,
			"SELECT * FROM (actor INNER JOIN movie_cast ON (actor.act_id = movie_cast.act_id)) AS result WHERE result.act_gender = 'F'"
		)
	}

}