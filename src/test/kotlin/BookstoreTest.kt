import de.ruegnerlukas.sqldsl.core.builders.query.all
import de.ruegnerlukas.sqldsl.core.builders.query.query
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.EqualCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.GreaterThanCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.LessThanCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.OrCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.SubOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.literal
import de.ruegnerlukas.sqldsl.core.syntax.from.alias
import de.ruegnerlukas.sqldsl.core.syntax.from.joinLeft
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.ColumnRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.alias
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.asc
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.desc
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.get
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRefContainer
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.alias
import de.ruegnerlukas.sqldsl.core.syntax.select.CountAllSelectExpression
import de.ruegnerlukas.sqldsl.db.Book
import de.ruegnerlukas.sqldsl.db.BookLanguage
import de.ruegnerlukas.sqldsl.db.CustomerOrder
import de.ruegnerlukas.sqldsl.sqlite.NewSqliteQueryGenerator

fun main() {

	val tblOrders = CustomerOrder.alias("orders")
	val tblTitles = TableRefContainer()
	val colCount = ColumnRefContainer<Int>()

	val query = query()
		.select(
			all(),
			all(Book),
			Book.title,
			tblOrders[CustomerOrder.orderId],
			tblOrders[CustomerOrder.orderDate].alias("date"),
			colCount.fill(CountAllSelectExpression())
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
			joinLeft {
				left(Book)
				right(tblOrders)
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
					tblOrders[CustomerOrder.orderDate],
					literal("01.01.1990")
				)
			)
		)
		.groupBy(
			tblOrders[CustomerOrder.shippingMethodId]
		)
		.having(
			GreaterThanCondition(
				SubOperation(colCount, literal(2)),
				literal(8)
			)
		)
		.orderBy(
			Book.title.asc(),
			tblOrders[CustomerOrder.shippingMethodId].desc()
		)
		.limit(50)
		.build()

	println(
		NewSqliteQueryGenerator().build(query)
	)

}