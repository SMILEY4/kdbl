package sqldsl

import de.ruegnerlukas.sqldsl.generators.generic.GenericDeleteGenerator
import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class DeleteTest {

	private val generator = GenericDeleteGenerator(GenericGeneratorContext())

	private fun assertQuery(query: DeleteStatement, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}

	@Test
	fun delete1() {
		val query = DeleteStatement(
			target = Movie,
			where = WhereStatement(
				EqualCondition(
					Movie.id,
					IntLiteral(42)
				)
			),
			returning = ReturnAllColumnsStatement()
		)
		assertQuery(query, "DELETE FROM movie WHERE (movie.mov_id) = (42) RETURNING *")
	}


}