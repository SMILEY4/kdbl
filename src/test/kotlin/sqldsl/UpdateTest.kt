package sqldsl

import de.ruegnerlukas.sqldsl.codegen.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.codegen.generic.GenericUpdateGenerator
import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.OnConflict

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


	private fun printQuery(name: String, query: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement?) {
		println("--QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println("$str;")
		} else {
			println("--")
		}
		println()
	}

	private fun update1() = de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement(
		onConflict = OnConflict.ABORT,
		target = Movie,
		set = de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateSetStatement(
			listOf(
				de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateExpression(
					Movie.title,
					de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("New Title")
				),
				de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateExpression(
					Movie.releaseCountry,
					de.ruegnerlukas.sqldsl.dsl.grammar.expr.StringLiteral("Somewhere")
				),
			)
		),
		where = de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement(
			de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(
				Movie.id,
				de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(42)
			)
		),
		returning = de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(
			listOf(
				Movie.title,
				Movie.releaseCountry
			)
		)
	)


	private fun update2(): de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement {
		val result = de.ruegnerlukas.sqldsl.dsl.grammar.table.DerivedTable("result")
		return de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement(
			onConflict = OnConflict.ABORT,
			target = Sale,
			set = de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateSetStatement(
				listOf(
					de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateExpression(Sale.amount, result.columnInt("sum")),
				)
			),
			from = de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement(
				listOf(
					result.assign(
						de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<AnyValueType>(
							select = de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(
								listOf(
									de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn(
										de.ruegnerlukas.sqldsl.dsl.grammar.expr.SumAggFunction(
											Logs.marks
										), "sum"
									)
								)
							),
							from = de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement(
								listOf(
									Logs
								)
							)
						)
					)
				)
			),
			where = de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement(
				de.ruegnerlukas.sqldsl.dsl.grammar.expr.EqualCondition(
					Sale.id,
					de.ruegnerlukas.sqldsl.dsl.grammar.expr.IntLiteral(32)
				)
			)
		)
	}

}