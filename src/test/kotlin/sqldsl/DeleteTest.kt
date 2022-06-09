package sqldsl

import de.ruegnerlukas.sqldsl.builders.DeleteBuilderEndStep
import de.ruegnerlukas.sqldsl.builders.SQL
import de.ruegnerlukas.sqldsl.builders.isEqual
import de.ruegnerlukas.sqldsl.generators.generic.GenericDeleteGenerator
import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class DeleteTest {

	private val generator = GenericDeleteGenerator(GenericGeneratorContext())

	private fun assertQuery(query: DeleteBuilderEndStep, expected: String) {
		val strQuery = generator.buildString(query.build())
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun delete1() {
		val query = SQL
			.deleteFrom(Movie)
			.where(Movie.id.isEqual(42))
			.returningAll()
		assertQuery(query, "DELETE FROM movie WHERE (movie.mov_id) = (42) RETURNING *")
	}


}