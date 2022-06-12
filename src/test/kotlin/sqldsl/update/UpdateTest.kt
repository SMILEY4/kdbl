package sqldsl.update

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.builder.alias
import de.ruegnerlukas.sqldsl.builder.assign
import de.ruegnerlukas.sqldsl.builder.isEqual
import de.ruegnerlukas.sqldsl.builder.sum
import de.ruegnerlukas.sqldsl.codegen.BaseGenerator
import de.ruegnerlukas.sqldsl.dsl.expr.DerivedTable
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement
import de.ruegnerlukas.sqldsl.dsl.statements.set
import org.junit.jupiter.api.Test
import sqldsl.Logs
import sqldsl.Movie
import sqldsl.Sale
import kotlin.test.assertEquals


class UpdateTest {

	private fun assertQuery(stmt: UpdateStatement, expected: String) {
		val strQuery = BaseGenerator().update(stmt).buildString()
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	@Test
	fun update1() {
		val query = SQL.update()
			.target(Movie)
			.or(OnConflict.FAIL)
			.set(
				Movie.title.set("New Title"),
				Movie.releaseCountry.set("Somewhere")
			)
			.where(Movie.id.isEqual(42))
			.returning(Movie.title, Movie.releaseCountry)
			.build()
		assertQuery(query, "UPDATE OR FAIL movie SET movie.mov_title = 'New Title', movie.mov_rel_country = 'Somewhere' WHERE movie.mov_id = 42 RETURNING movie.mov_title, movie.mov_rel_country")
	}


	@Test
	fun update2() {
		val result = DerivedTable("result")
		val query = SQL.update()
			.target(Sale)
			.set(
				Sale.amount.set(result.columnInt("sum"))
			)
			.from(
				SQL.query()
					.select(
						Logs.marks.sum().alias("sum")
					)
					.from(Logs)
					.build<Int>()
					.assign(result)
			)
			.where(Sale.id.isEqual(32))
			.build()
		assertQuery(query, "UPDATE sale_mast SET sale_mast.sale_amt = result.sum FROM (SELECT (SUM(logs.marks)) AS sum FROM logs) AS result WHERE sale_mast.sale_id = 32")
	}

}