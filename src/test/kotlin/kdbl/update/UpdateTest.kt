package kdbl.update

import de.ruegnerlukas.kdbl.builder.AggFn.sum
import de.ruegnerlukas.kdbl.builder.SQL
import de.ruegnerlukas.kdbl.builder.alias
import de.ruegnerlukas.kdbl.builder.assign
import de.ruegnerlukas.kdbl.builder.isEqual
import de.ruegnerlukas.kdbl.dsl.expression.DerivedTable
import de.ruegnerlukas.kdbl.dsl.statements.set
import org.junit.jupiter.api.Test
import kdbl.schema.Logs
import kdbl.Movie
import kdbl.schema.Sale
import kdbl.utils.assertQuery


class UpdateTest {

	@Test
	fun update1() {
		val query = SQL
			.update(Movie)
			.set(
				Movie.title.set("New Title"),
				Movie.releaseCountry.set("Somewhere")
			)
			.where(Movie.id.isEqual(42))
			.returning(Movie.title, Movie.releaseCountry)
		assertQuery(
			query,
			"UPDATE movie SET mov_title = 'New Title', mov_rel_country = 'Somewhere' WHERE movie.mov_id = 42 RETURNING movie.mov_title, movie.mov_rel_country"
		)
	}


	@Test
	fun update2() {
		val result = DerivedTable("result")
		val query = SQL
			.update(Sale)
			.set(
				Sale.amount.set(result.columnInt("sum"))
			)
			.where(Sale.id.isEqual(32))
			.from(
				SQL
					.select(
						Logs.marks.sum().alias("sum")
					)
					.from(Logs)
					.assign(result)
			)
		assertQuery(
			query,
			"UPDATE sale_mast SET sale_amt = result.sum FROM (SELECT (SUM(logs.marks)) AS sum FROM logs) AS result WHERE sale_mast.sale_id = 32"
		)
	}

}