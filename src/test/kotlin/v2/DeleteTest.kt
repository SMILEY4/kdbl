package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericDeleteGenerator
import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

fun main() {
	DeleteTest().all()
}


class DeleteTest {

	private val generator = GenericDeleteGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", delete1())
	}


	private fun printQuery(name: String, query: DeleteStatement?) {
		println("--QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println("$str;")
		} else {
			println("--")
		}
		println()
	}

	private fun delete1() = DeleteStatement(
		target = Movie,
		where = WhereStatement(
			EqualCondition(
				Movie.id,
				IntLiteral(42)
			)
		),
		returning = ReturnAllColumnsStatement()
	)


}