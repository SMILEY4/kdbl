package sqldsl.insert

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.codegen.BaseGenerator
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import org.junit.jupiter.api.Test
import sqldsl.Actor
import kotlin.test.assertEquals


class InsertTest {

	private fun assertQuery(stmt: InsertStatement, expected: String) {
		val strQuery = BaseGenerator().insert(stmt).buildString()
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun insert1() {

		val query = SQL.insert()
			.into(Actor)
			.or(OnConflict.FAIL)
			.fields(Actor.id, Actor.fName, Actor.lName, Actor.gender)
			.items(
				SQL.item()
					.set(Actor.id, 101)
					.set(Actor.fName, "James")
					.set(Actor.lName, "Steward")
					.set(Actor.gender, "M"),
				SQL.item()
					.set(Actor.id, 102)
					.set(Actor.fName, "Deborah")
					.set(Actor.lName, "Kerr")
					.set(Actor.gender, "F")
			)
			.build()
		assertQuery(
			query,
			"INSERT OR FAIL INTO actor (act_id, act_fname, act_lname, act_gender) VALUES (101, 'James', 'Steward', 'M'), (102, 'Deborah', 'Kerr', 'F')"
		)
	}


	@Test
	fun insert2() {
		val query = SQL.insert()
			.into(Actor)
			.or(OnConflict.IGNORE)
			.allFields()
			.query(
				SQL.query()
					.selectAll()
					.from(Actor)
					.build<Any>()
			)
			.returning(Actor.id, Actor.fName)
			.build()
		assertQuery(query, "INSERT OR IGNORE INTO actor VALUES (SELECT * FROM actor) RETURNING actor.act_id, actor.act_fname")
	}

}