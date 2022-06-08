package sqldsl

import de.ruegnerlukas.sqldsl.builders.InsertBuilderEndStep
import de.ruegnerlukas.sqldsl.builders.SQL
import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericInsertGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class InsertTest {

	private val generator = GenericInsertGenerator(GenericGeneratorContext())

	private fun assertQuery(query: InsertBuilderEndStep, expected: String) {
		val strQuery = generator.buildString(query.build())
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun insert1() {
		val query = SQL
			.insertInto(Actor)
			.orFail()
			.fields(Actor.id, Actor.fName, Actor.lName, Actor.gender)
			.item(
				mapOf(
					Actor.id to IntLiteral(101),
					Actor.fName to StringLiteral("James"),
					Actor.lName to StringLiteral("Steward"),
					Actor.gender to StringLiteral("M")
				)
			)
			.item(
				mapOf(
					Actor.id to IntLiteral(102),
					Actor.fName to StringLiteral("Deborah"),
					Actor.lName to StringLiteral("Kerr"),
					Actor.gender to StringLiteral("F")
				)
			)
		assertQuery(
			query,
			"INSERT OR FAIL INTO actor (actor.act_id, actor.act_fname, actor.act_lname, actor.act_gender) VALUES (101, 'James', 'Steward', 'M'), (102, 'Deborah', 'Kerr', 'F')"
		)
	}


	@Test
	fun insert2() {
		val query = SQL
			.insertInto(Actor)
			.orIgnore()
			.allFields()
			.fromQuery(
				SQL
					.selectAll()
					.from(Actor)
			)
			.returning(Actor.id, Actor.fName)
		assertQuery(query, "INSERT OR IGNORE INTO actor VALUES (SELECT * FROM actor) RETURNING actor.act_id, actor.act_fname")
	}

}