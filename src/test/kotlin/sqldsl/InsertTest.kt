package sqldsl

import de.ruegnerlukas.sqldsl.dsl.builders.InsertBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.builders.SQL
import de.ruegnerlukas.sqldsl.codegen.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.codegen.generic.GenericInsertGenerator
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
					Actor.id to de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(101),
					Actor.fName to de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("James"),
					Actor.lName to de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("Steward"),
					Actor.gender to de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("M")
				)
			)
			.item(
				mapOf(
					Actor.id to de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(102),
					Actor.fName to de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("Deborah"),
					Actor.lName to de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("Kerr"),
					Actor.gender to de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("F")
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