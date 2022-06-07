package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericInsertGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertAllColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertValuesExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.schema.OnConflict

fun main() {
	InsertTest().all()
}


class InsertTest {

	private val generator = GenericInsertGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", insert1())
		printQuery("2", insert2())
	}


	private fun printQuery(name: String, query: InsertStatement?) {
		println("--QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println("$str;")
		} else {
			println("--")
		}
		println()
	}

	private fun insert1() = InsertStatement(
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

	private fun insert2() = InsertStatement(
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

}