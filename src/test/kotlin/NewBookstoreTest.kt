import de.ruegnerlukas.sqldsl.core.builders.QueryBuilder
import de.ruegnerlukas.sqldsl.core.grammar.expression.EqualCondition
import de.ruegnerlukas.sqldsl.core.grammar.refs.ref
import de.ruegnerlukas.sqldsl.db.Book
import de.ruegnerlukas.sqldsl.db.BookLanguage
import de.ruegnerlukas.sqldsl.db.CustomerOrder
import de.ruegnerlukas.sqldsl.sqlite.NewSqliteQueryGenerator

fun main() {

	val query = QueryBuilder()
		.select {
			all()
			all(Book)
			column(CustomerOrder.orderId)
			column(CustomerOrder.orderDate, "date")
		}
		.from {
			table(Book)
			table(CustomerOrder, "orders")
			subQuery(
				QueryBuilder()
					.select { column(Book.title) }
					.from { table(Book) }
					.build(),
				"titles"
			)
			joinLeft {
				left(Book, "b")
				right(BookLanguage, "l")
				on(
					EqualCondition(
						ref(Book.languageId),
						ref(BookLanguage.languageId)
					)
				)
			}
		}
		.build()

	println(
		NewSqliteQueryGenerator().build(query)
	)

}