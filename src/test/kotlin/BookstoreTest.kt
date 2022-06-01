import de.ruegnerlukas.sqldsl.core.builders.query.all
import de.ruegnerlukas.sqldsl.core.builders.query.query
import de.ruegnerlukas.sqldsl.core.grammar.expression.EqualCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.LessThanCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.OrCondition
import de.ruegnerlukas.sqldsl.core.grammar.expression.literal
import de.ruegnerlukas.sqldsl.core.grammar.from.alias
import de.ruegnerlukas.sqldsl.core.grammar.from.joinLeft
import de.ruegnerlukas.sqldsl.core.grammar.refs.alias
import de.ruegnerlukas.sqldsl.core.grammar.refs.asc
import de.ruegnerlukas.sqldsl.core.grammar.refs.desc
import de.ruegnerlukas.sqldsl.core.grammar.refs.get
import de.ruegnerlukas.sqldsl.db.Book
import de.ruegnerlukas.sqldsl.db.BookLanguage
import de.ruegnerlukas.sqldsl.db.CustomerOrder
import de.ruegnerlukas.sqldsl.sqlite.NewSqliteQueryGenerator

fun main() {

	val refOrders = CustomerOrder.alias("orders")

	val query = query()
		.select(
			all(),
			all(Book),
			Book.title,
			refOrders[CustomerOrder.orderId],
			refOrders[CustomerOrder.orderDate].alias("date")
		)
		.from(
			Book,
			refOrders,
			query()
				.select(Book.title)
				.from(Book)
				.build()
				.alias("titles"),
			joinLeft {
				left(Book)
				right(refOrders)
				on(
					EqualCondition(
						Book.languageId,
						BookLanguage.languageId
					)
				)
			}
		)
		.where(
			OrCondition(
				EqualCondition(
					Book.publisherId,
					literal(42)
				),
				LessThanCondition(
					refOrders[CustomerOrder.orderDate],
					literal("01.01.1990")
				)
			)
		)
		.groupBy(
			refOrders[CustomerOrder.shippingMethodId]
		)
		.orderBy(
			Book.title.asc(),
			refOrders[CustomerOrder.shippingMethodId].desc()
		)
		.limit(50)
		.build()

	println(
		NewSqliteQueryGenerator().build(query)
	)

}