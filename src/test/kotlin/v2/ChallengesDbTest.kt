package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement

fun main() {
	ChallengesDbTest().all()
}


/**
 * https://www.w3resource.com/sql-exercises/challenges-1/index.php
 */
class ChallengesDbTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
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
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-2.php
	 */
	fun query2() {

	}

	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-4.php
	 */
	fun query4() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-5.php
	 */
	fun query5() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-6.php
	 */
	fun query6() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-9.php
	 */
	fun query9() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-31.php
	 */
	fun query31() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-46.php
	 */
	fun query46() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-48.php
	 */
	fun query48() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-53.php
	 */
	fun query53() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-54.php
	 */
	fun query54() {

	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-59.php
	 */
	fun query59() {

	}

}