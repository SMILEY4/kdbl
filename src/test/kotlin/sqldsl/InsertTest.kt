package sqldsl

import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericInsertGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertAllColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertValuesExpression
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.schema.OnConflict
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals



class InsertTest {

	private val generator = GenericInsertGenerator(GenericGeneratorContext())

	private fun assertQuery(query: InsertStatement, expected: String) {
		val strQuery = generator.buildString(query)
		println(strQuery)
		assertEquals(expected, strQuery)
	}

	@Test
	fun insert1() {
		val query = InsertStatement(
			onConflict = OnConflict.FAIL,
			target = Actor,
			fields = InsertColumnsStatement(
				listOf(
					Actor.id, Actor.fName, Actor.lName, Actor.gender
				)
			),
			content = InsertValuesExpression(
				listOf(
					mapOf(
						Actor.id to IntLiteral(101),
						Actor.fName to StringLiteral("James"),
						Actor.lName to StringLiteral("Steward"),
						Actor.gender to StringLiteral("M")
					),
					mapOf(
						Actor.id to IntLiteral(102),
						Actor.fName to StringLiteral("Deborah"),
						Actor.lName to StringLiteral("Kerr"),
						Actor.gender to StringLiteral("F")
					)
				)
			)
		)
		assertQuery(query, "INSERT OR FAIL INTO actor (actor.act_id, actor.act_fname, actor.act_lname, actor.act_gender) VALUES (101, 'James', 'Steward', 'M'), (102, 'Deborah', 'Kerr', 'F')")
	}

	@Test
	fun insert2() {
		val query = InsertStatement(
			onConflict = OnConflict.IGNORE,
			target = Actor,
			fields = InsertAllColumnsStatement(),
			content = QueryStatement(
				select = SelectStatement(
					listOf(
						AllSelectExpression()
					)
				),
				from = FromStatement(
					listOf(
						Actor
					)
				)
			),
			returning = ReturnColumnsStatement(
				listOf(
					Actor.id,
					Actor.fName
				)
			)
		)
		assertQuery(query, "INSERT OR IGNORE INTO actor VALUES (SELECT * FROM actor) RETURNING actor.act_id, actor.act_fname")
	}

}