package v2

import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateExpression
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateSetStatement
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl2.schema.OnConflict

fun main() {
	MiscDbTests().all()
}


class UpdateTest {

	fun all() {
		println()
		printQuery("1", update1())
		printQuery("2", update2())
	}


	private fun printQuery(name: String, query: UpdateStatement?) {
		println("--QUERY $name:")
		if (query != null) {
//			val str = generator.buildString(query)
//			println("$str;")
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
					UpdateExpression(Sale.amount, result.column("sum")),
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