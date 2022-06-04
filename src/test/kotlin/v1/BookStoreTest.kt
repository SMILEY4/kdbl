package v1

import de.ruegnerlukas.sqldsl.core.builders.query.query
import de.ruegnerlukas.sqldsl.core.syntax.expression.condition.EqualCondition
import de.ruegnerlukas.sqldsl.core.syntax.expression.literal.IntLiteralValue
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.CountAllOperation
import de.ruegnerlukas.sqldsl.core.syntax.expression.operation.CountDistinctOperation
import de.ruegnerlukas.sqldsl.core.syntax.from.joinInner
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.alias
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.asc
import de.ruegnerlukas.sqldsl.core.syntax.refs.column.get
import de.ruegnerlukas.sqldsl.core.syntax.refs.table.alias
import de.ruegnerlukas.sqldsl.core.syntax.select.all
import de.ruegnerlukas.sqldsl.core.syntax.statements.QueryStatement
import de.ruegnerlukas.sqldsl.db.CustomerOrder
import de.ruegnerlukas.sqldsl.db.OrderLine
import de.ruegnerlukas.sqldsl.sqlite.SQLiteQueryGenerator

/**
 * QUERIES AND SCHEMA FROM
 * https://www.databasestar.com/complex-sql-query-example/
 */

fun main() {
	println(SQLiteQueryGenerator.build(query1()))
	println(SQLiteQueryGenerator.build(query2()))
	println(SQLiteQueryGenerator.build(query3()))
	println(SQLiteQueryGenerator.build(query4()))
	println(SQLiteQueryGenerator.build(query5()))
}


/**
 * SELECT order_date
 * FROM cust_order;
 */
fun query1(): QueryStatement {
	return query()
		.select(CustomerOrder.orderDate)
		.from(CustomerOrder)
		.build()
}


/**
 * SELECT order_date
 * FROM cust_order
 * ORDER BY order_date ASC;
 */
fun query2(): QueryStatement {
	return query()
		.select(CustomerOrder.orderDate)
		.from(CustomerOrder)
		.orderBy(CustomerOrder.orderDate.asc())
		.build()
}


/**
 * SELECT order_date, COUNT(*)
 * FROM cust_order
 * GROUP BY order_date
 * ORDER BY order_date ASC;
 */
fun query3(): QueryStatement {
	return query()
		.select(
			CustomerOrder.orderDate,
			CountAllOperation().alias("count")
		)
		.from(CustomerOrder)
		.groupBy(CustomerOrder.orderDate)
		.orderBy(CustomerOrder.orderDate.asc())
		.build()
}


/**
 * SELECT *
 * FROM order_line
 * WHERE order_id = 3;
 */
fun query4(): QueryStatement {
	return query()
		.select(all())
		.from(CustomerOrder)
		.where(EqualCondition(CustomerOrder.orderId, IntLiteralValue(3)))
		.build()
}


/**
 * SELECT
 * 		DATE_FORMAT(co.order_date, '%Y-%m-%d') AS order_day,
 * 		COUNT(DISTINCT co.order_id) AS num_orders,
 * 		COUNT(ol.book_id) AS num_books
 * FROM cust_order co
 * 		INNER JOIN order_line ol ON co.order_id = ol.order_id
 * GROUP BY DATE_FORMAT(co.order_date, '%Y-%m-%d')
 * ORDER BY co.order_date ASC;
 */
fun query5(): QueryStatement {
	val tblCO = CustomerOrder.alias("co")
	val tblOL = OrderLine.alias("ol")
	return query()
		.select(
			tblCO[CustomerOrder.orderDate].alias("order_day"),
			CountDistinctOperation(tblCO[CustomerOrder.orderId]).alias("num_orders"),
			CountDistinctOperation(tblOL[OrderLine.orderId]).alias("num_books")
		)
		.from(
			tblCO.joinInner(tblOL).on(EqualCondition(tblCO[CustomerOrder.orderId], tblOL[OrderLine.orderId]))
		)
		.groupBy(tblCO[CustomerOrder.orderDate])
		.orderBy(tblCO[CustomerOrder.orderDate].asc())
		.build()
}

