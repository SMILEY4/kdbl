package sqldsl

import de.ruegnerlukas.sqldsl.builders.CreateBuilderEndStep
import de.ruegnerlukas.sqldsl.builders.SQL
import de.ruegnerlukas.sqldsl.generators.generic.GenericCreateGenerator
import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CreateTest {

	private val generator = GenericCreateGenerator(GenericGeneratorContext())

	private fun assertQuery(query: CreateBuilderEndStep, expected: String) {
		val strQuery = generator.buildString(query.build())
		println(strQuery)
		assertEquals(expected, strQuery)
	}

	@Test
	fun test1() {
		val query = SQL
			.createTable(Movie)
			.ifNotExists()
		assertQuery(query, "CREATE TABLE IF NOT EXISTS movie (mov_id INT PRIMARY KEY, mov_title TEXT NOT NULL, mov_year INT NOT NULL, mov_time INT NOT NULL, mov_lang TEXT NOT NULL, mov_dt_rel TEXT NOT NULL, mov_rel_country TEXT NOT NULL)")
	}

	@Test
	fun test2() {
		val query = SQL
			.createTable(Actor)
		assertQuery(query, "CREATE TABLE actor (act_id INT PRIMARY KEY, act_fname TEXT NOT NULL, act_lname TEXT NOT NULL, act_gender TEXT NOT NULL)")
	}

	@Test
	fun test3() {
		val query = SQL
			.createTable(Rating)
		assertQuery(query, "CREATE TABLE rating (mov_id INT REFERENCES movie (mov_id), rev_id INT REFERENCES reviewer (rev_id), rev_stars INT NOT NULL, num_o_ratings INT NOT NULL)")
	}

	@Test
	fun test4() {
		val query = SQL
			.createTable(Exam)
		assertQuery(query, "CREATE TABLE exam_test (exam_id INT PRIMARY KEY, subject_id INT PRIMARY KEY, exam_year INT PRIMARY KEY, no_of_student INT ) PRIMARY KEY (exam_id, subject_id, exam_year) ON CONFLICT ABORT")
	}

}