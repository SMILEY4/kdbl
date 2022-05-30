import de.ruegnerlukas.sqldsl.core.actions.query.Dir
import de.ruegnerlukas.sqldsl.core.actions.query.alias
import de.ruegnerlukas.sqldsl.core.actions.query.all
import de.ruegnerlukas.sqldsl.core.actions.query.count
import de.ruegnerlukas.sqldsl.core.actions.query.eq
import de.ruegnerlukas.sqldsl.core.actions.query.get
import de.ruegnerlukas.sqldsl.core.actions.query.innerJoin
import de.ruegnerlukas.sqldsl.core.actions.query.order
import de.ruegnerlukas.sqldsl.core.actions.query.query
import de.ruegnerlukas.sqldsl.db.CustomerOrder
import de.ruegnerlukas.sqldsl.db.OrderLine
import de.ruegnerlukas.sqldsl.sqlite.SQLiteQueryGenerator

// SCHEMA AND EXAMPLES FROM:
// https://www.databasestar.com/complex-sql-query-example/

fun main() {

	val builder = SQLiteQueryGenerator()

	/*
	SELECT order_date
	FROM cust_order;
	 */
	println(
		builder.build(
			query()
				.select(CustomerOrder.orderDate)
				.from(CustomerOrder)
				.build()
		)
	)

	/*
	SELECT order_date
	FROM cust_order
	ORDER BY order_date ASC;
	 */
	println(
		builder.build(
			query()
				.select(CustomerOrder.orderDate)
				.from(CustomerOrder)
				.orderBy(CustomerOrder.orderDate order Dir.ASC)
				.build()
		)
	)

	/*
	SELECT order_date, COUNT(*)
	FROM cust_order
	ORDER BY order_date ASC;
	 */
	println(
		builder.build(
			query()
				.select(CustomerOrder.orderDate, count().alias("count"))
				.from(CustomerOrder)
				.orderBy(CustomerOrder.orderDate order Dir.ASC)
				.build()
		)
	)

	/*
	SELECT order_date, COUNT(*)
	FROM cust_order
	GROUP BY order_date
	ORDER BY order_date ASC;
	 */
	println(
		builder.build(
			query()
				.select(CustomerOrder.orderDate, count().alias("count"))
				.from(CustomerOrder)
				.orderBy(CustomerOrder.orderDate order Dir.ASC)
				.build()
//		.groupBy(CustomerOrder.orderDate)
		)
	)

	/*
	SELECT DATE_FORMAT(order_date, '%Y-%m-%d'), COUNT(*)
	FROM cust_order
	GROUP BY DATE_FORMAT(order_date, '%Y-%m-%d')
	ORDER BY order_date ASC;
	 */

	//TODO

	/*
	SELECT DATE_FORMAT(order_date, '%Y-%m-%d') AS order_day, COUNT(*) AS num_orders
	FROM cust_order
	 */

	//TODO

	/*
	SELECT *
	FROM cust_order;
	 */
	println(
		builder.build(
			query()
				.select(all())
				.from(CustomerOrder)
				.build()
		)
	)
	/*
	SELECT *
	FROM order_line
	WHERE order_id = 3;
	 */
	println(
		builder.build(
			query()
				.select(all())
				.from(CustomerOrder)
				.build()
//		.where(CustomerOrder.orderId eq const("3"))
		)
	)

	/*
	SELECT
		co.order_date AS order_day,
		COUNT(co.order_id) AS num_orders
	FROM cust_order co
		INNER JOIN order_line ol ON co.order_id = ol.order_id
	GROUP BY co.order_date
	ORDER BY co.order_date ASC;
	 */
	val co = CustomerOrder.alias("co")
	val ol = OrderLine.alias("ol")
	println(
		builder.build(
			query()
				.select(
					co[CustomerOrder.orderDate].alias("order_day"),
					count(co[CustomerOrder.orderId]).alias("num_orders")
				)
				.from(
					innerJoin(co, ol, co[CustomerOrder.orderId] eq ol[OrderLine.orderId])
				)
				.orderBy(co[CustomerOrder.orderDate] order Dir.ASC)
				.build()
//		.groupBy(co.col(CustomerOrder.orderDate))
		)
	)

	/*
	SELECT
		co.*,
		ol.*
	FROM cust_order co
		INNER JOIN order_line ol ON co.order_id = ol.order_id
	WHERE co.order_date = '2018-07-10';
	 */
	println(
		builder.build(
			query()
				.select(
					all(co),
					all(ol)
				)
				.from(
					innerJoin(co, ol, co[CustomerOrder.orderId] eq ol[OrderLine.orderId])
				)
				.build()
//		.where(
//			co.col(CustomerOrder.orderDate) eq const("2018-07-10")
//		)
		)
	)
}















