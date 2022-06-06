package v2

import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertAllColumnsExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertColumnsExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertValuesExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.schema.OnConflict

fun main() {
	MiscDbTests().all()
}


class InsertTest {

	fun all() {
		println()
		printQuery("1", insert1())
		printQuery("2", insert2())
		printQuery("3", insert3())
	}


	private fun printQuery(name: String, query: InsertStatement?) {
		println("--QUERY $name:")
		if (query != null) {
//			val str = generator.buildString(query)
//			println("$str;")
		} else {
			println("--")
		}
		println()
	}

	private fun insert1() = InsertStatement(
		onConflict = OnConflict.FAIL,
		target = Actor,
		fields = InsertColumnsExpression(
			listOf(
				Actor.id, Actor.fName, Actor.lName, Actor.gender
			)
		),
		content = InsertValuesExpression(
			listOf(
				mapOf(
					Actor.id to 101,
					Actor.fName to "James",
					Actor.lName to "Steward",
					Actor.gender to "M"
				),
				mapOf(
					Actor.id to 102,
					Actor.fName to "Deborah",
					Actor.lName to "Kerr",
					Actor.gender to "F"
				)
			)
		)
	)


	private fun insert2() = InsertStatement(
		onConflict = OnConflict.IGNORE,
		target = Actor,
		fields = InsertAllColumnsExpression(),
		content = InsertValuesExpression(
			listOf(
				mapOf(
					Actor.id to 101,
					Actor.fName to "James",
					Actor.lName to "Steward",
					Actor.gender to "M"
				),
				mapOf(
					Actor.id to 102,
					Actor.fName to "Deborah",
					Actor.lName to "Kerr",
					Actor.gender to "F"
				)
			)
		)
	)


	private fun insert3() = InsertStatement(
		onConflict = OnConflict.IGNORE,
		target = Actor,
		fields = InsertAllColumnsExpression(),
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

}