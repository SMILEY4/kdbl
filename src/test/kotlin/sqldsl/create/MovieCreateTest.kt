package sqldsl.create

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.codegen.BaseGenerator
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import org.junit.jupiter.api.Test
import sqldsl.Actor
import sqldsl.Exam
import sqldsl.Movie
import sqldsl.Rating
import kotlin.test.assertEquals

class CreateTest {

	private fun assertQuery(stmt: CreateTableStatement, expected: String) {
		val strQuery = BaseGenerator().create(stmt).buildString()
		println(strQuery)
		assertEquals(expected, strQuery)
	}

	@Test
	fun test1() {
		val query = SQL
			.createIfNotExists(Movie)
		assertQuery(query, "CREATE TABLE IF NOT EXISTS movie (mov_id INT PRIMARY KEY, mov_title TEXT NOT NULL, mov_year INT NOT NULL, mov_time INT NOT NULL, mov_lang TEXT NOT NULL, mov_dt_rel TEXT NOT NULL, mov_rel_country TEXT NOT NULL)")
	}

	@Test
	fun test2() {
		val query = SQL
			.create(Actor)
		assertQuery(query, "CREATE TABLE actor (act_id INT PRIMARY KEY, act_fname TEXT NOT NULL, act_lname TEXT NOT NULL, act_gender TEXT NOT NULL)")
	}

	@Test
	fun test3() {
		val query = SQL
			.create(Rating)
		assertQuery(query, "CREATE TABLE rating (mov_id INT REFERENCES movie (mov_id), rev_id INT REFERENCES reviewer (rev_id), rev_stars INT NOT NULL, num_o_ratings INT NOT NULL)")
	}

	@Test
	fun test4() {
		val query = SQL
			.create(Exam)
		assertQuery(query, "CREATE TABLE exam_test (exam_id INT, subject_id INT, exam_year INT, no_of_student INT) PRIMARY KEY (exam_id, subject_id, exam_year)")
	}

}