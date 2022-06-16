package sqldsl.insert

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.codegen.SQLCodeGenerator
import de.ruegnerlukas.sqldsl.dsl.expression.OnConflict
import de.ruegnerlukas.sqldsl.dsl.statements.InsertBuilderEndStep
import org.junit.jupiter.api.Test
import sqldsl.Actor
import sqldsl.utils.assertQuery
import kotlin.test.assertEquals


class InsertTest {

	@Test
	fun insert1() {
		val query = SQL
			.insert()
			.into(Actor)
			.or(OnConflict.FAIL)
			.columns(Actor.id, Actor.fName, Actor.lName, Actor.gender)
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
		assertQuery(
			query,
			"INSERT OR FAIL INTO actor (act_id, act_fname, act_lname, act_gender) VALUES (101, 'James', 'Steward', 'M'), (102, 'Deborah', 'Kerr', 'F')"
		)
	}


	@Test
	fun insert2() {
		val query = SQL
			.insert()
			.into(Actor)
			.or(OnConflict.IGNORE)
			.allColumns()
			.query(
				SQL
					.selectAll()
					.from(Actor)
			)
			.returning(Actor.id, Actor.fName)
		assertQuery(query, "INSERT OR IGNORE INTO actor VALUES (SELECT * FROM actor) RETURNING actor.act_id, actor.act_fname")
	}

}