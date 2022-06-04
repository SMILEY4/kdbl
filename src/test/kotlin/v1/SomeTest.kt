package v1

import de.ruegnerlukas.sqldsl.core.builders.query.query
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.EqualCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.GreaterThanCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.LessThanCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.OrCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.IntLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.CountAllOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.SubOperation
import de.ruegnerlukas.sqldsl.core.syntax.from.alias
import de.ruegnerlukas.sqldsl.core.syntax.from.joinLeft
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.alias
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.asc
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.desc
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.get
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.alias
import de.ruegnerlukas.sqldsl.core.syntax.select.all
import de.ruegnerlukas.sqldsl.db.Book
import de.ruegnerlukas.sqldsl.db.BookLanguage
import de.ruegnerlukas.sqldsl.db.CustomerOrder
import de.ruegnerlukas.sqldsl.sqlite.SQLiteQueryGenerator
import java.lang.Long.max
import java.lang.Long.min

fun main() {

	val tblOrders = CustomerOrder.alias("orders")
	val tblTitles = TableRefContainer()
	val colCount = ColumnRefContainer<Int>()
	val colPublisher = Book.publisherId.alias("publisher")

	val query = query()
		.select(
			all(),
			Book,
			Book.title,
			colPublisher,
			tblOrders[CustomerOrder.orderId],
			tblOrders[CustomerOrder.orderDate].alias("date"),
			colCount.fill(CountAllOperation().alias("count")),
		)
		.from(
			Book,
			tblOrders,
			tblTitles.fill(
				query()
					.select(Book.title)
					.from(Book)
					.build()
					.alias("titles")
			),
			Book.joinLeft(tblOrders).on(
				EqualCondition(
					Book.languageId,
					BookLanguage.languageId
				)
			)
		)
		.where(
			OrCondition(
				EqualCondition(
					colPublisher,
					IntLiteralValue(42)
				),
				LessThanCondition(
					tblOrders[CustomerOrder.orderDate],
					IntLiteralValue(1990)
				)
			)
		)
		.groupBy(
			tblOrders[CustomerOrder.shippingMethodId]
		)
		.having(
			GreaterThanCondition(
				SubOperation(colCount, IntLiteralValue(2)),
				IntLiteralValue(8)
			)
		)
		.orderBy(
			Book.title.asc(),
			tblOrders[CustomerOrder.shippingMethodId].desc()
		)
		.limit(50)
		.build()

	val nIterations = 1000

	var min = Long.MAX_VALUE
	var max = Long.MIN_VALUE
	var sum = 0L

	for (i in 1..nIterations) {
		val ts = System.nanoTime()
		SQLiteQueryGenerator.build(query)
		val te = System.nanoTime()
		val t = (te-ts)
		min = min(min, t)
		max = max(max, t)
		sum += t
	}

	println("MIN $min")
	println("MAX $min")
	println("AVG ${sum.toDouble()/nIterations.toDouble()}")

}