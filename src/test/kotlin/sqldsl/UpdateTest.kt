package sqldsl

import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericUpdateGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl.grammar.update.UpdateExpression
import de.ruegnerlukas.sqldsl.grammar.update.UpdateSetStatement
import de.ruegnerlukas.sqldsl.grammar.update.UpdateStatement
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl.schema.OnConflict

fun main() {
	UpdateTest().all()
}


class UpdateTest {

	private val generator = GenericUpdateGenerator(GenericGeneratorContext())


	fun all() {
		println()
		printQuery("1", update1())
		printQuery("2", update2())
	}


	private fun printQuery(name: String, query: UpdateStatement?) {
		println("--QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println("$str;")
		} else {
			println("--")
		}
		println()
	}

	private fun update1() = UpdateStatement(
		onConflict = OnConflict.ABORT,
		target = Movie,
		set = UpdateSetStatement(
			listOf(
				UpdateExpression(Movie.title, StringLiteral("New Title")),
				UpdateExpression(Movie.releaseCountry, StringLiteral("Somewhere")),
			)
		),
		where = WhereStatement(
			EqualCondition(
				Movie.id,
				IntLiteral(42)
			)
		),
		returning = ReturnColumnsStatement(
			listOf(
				Movie.title,
				Movie.releaseCountry
			)
		)
	)


	private fun update2(): UpdateStatement {
		val result = DerivedTable("result")
		return UpdateStatement(
			onConflict = OnConflict.ABORT,
			target = Sale,
			set = UpdateSetStatement(
				listOf(
					UpdateExpression(Sale.amount, result.columnInt("sum")),
				)
			),
			from = FromStatement(
				listOf(
					result.assign(
						QueryStatement(
							select = SelectStatement(
								listOf(
									AliasColumn(SumAggFunction(Logs.marks), "sum")
								)
							),
							from = FromStatement(
								listOf(
									Logs
								)
							)
						)
					)
				)
			),
			where = WhereStatement(
				EqualCondition(
					Sale.id,
					IntLiteral(32)
				)
			)
		)
	}

}