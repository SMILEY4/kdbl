package kdbl.insert

import de.ruegnerlukas.kdbl.builder.SQL
import org.junit.jupiter.api.Test
import kdbl.Actor
import kdbl.utils.assertQuery


class InsertTest {

	@Test
	fun insert1() {
		val query = SQL
			.insert()
			.into(Actor)
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
			"INSERT INTO actor (act_id, act_fname, act_lname, act_gender) VALUES (101, 'James', 'Steward', 'M'), (102, 'Deborah', 'Kerr', 'F')"
		)
	}


	@Test
	fun insert2() {
		val query = SQL
			.insert()
			.into(Actor)
			.allColumns()
			.query(
				SQL
					.selectAll()
					.from(Actor)
			)
			.returning(Actor.id, Actor.fName)
		assertQuery(query, "INSERT INTO actor VALUES (SELECT * FROM actor) RETURNING actor.act_id, actor.act_fname")
	}

}