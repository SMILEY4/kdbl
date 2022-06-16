package sqldsl.create

import de.ruegnerlukas.sqldsl.builder.SQL
import org.junit.jupiter.api.Test
import sqldsl.Actor
import sqldsl.Exam
import sqldsl.Movie
import sqldsl.Rating
import sqldsl.utils.assertQuery

class CreateTest {

	@Test
	fun test1() {
		val query = SQL
			.createIfNotExists(Movie)
		assertQuery(
			query,
			"CREATE TABLE IF NOT EXISTS movie (mov_id INTEGER PRIMARY KEY, mov_title TEXT NOT NULL, mov_year INTEGER NOT NULL, mov_time INTEGER NOT NULL, mov_lang TEXT NOT NULL, mov_dt_rel TEXT NOT NULL, mov_rel_country TEXT NOT NULL)"
		)
	}


	@Test
	fun test2() {
		val query = SQL
			.create(Actor)
		assertQuery(
			query,
			"CREATE TABLE actor (act_id INTEGER PRIMARY KEY, act_fname TEXT NOT NULL, act_lname TEXT NOT NULL, act_gender TEXT NOT NULL)"
		)
	}


	@Test
	fun test3() {
		val query = SQL
			.create(Rating)
		assertQuery(
			query,
			"CREATE TABLE rating (mov_id INTEGER REFERENCES movie (mov_id) ON DELETE NO ACTION ON UPDATE NO ACTION, rev_id INTEGER REFERENCES reviewer (rev_id) ON DELETE NO ACTION ON UPDATE NO ACTION, rev_stars INTEGER NOT NULL, num_o_ratings INTEGER NOT NULL)"
		)
	}


	@Test
	fun test4() {
		val query = SQL
			.create(Exam)
		assertQuery(
			query,
			"CREATE TABLE exam_test (exam_id INTEGER, subject_id INTEGER, exam_year INTEGER, no_of_student INTEGER) PRIMARY KEY (exam_id, subject_id, exam_year)"
		)
	}

}