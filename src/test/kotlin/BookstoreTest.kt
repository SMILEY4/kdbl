// SCHEMA AND EXAMPLES FROM:
// https://www.databasestar.com/complex-sql-query-example/

fun main() {

	/*
	SELECT order_date
	FROM cust_order;
	 */

//	query()
//		.select(CustomerOrder.orderDate)
//		.from(CustomerOrder)

	/*
	SELECT order_date
	FROM cust_order
	ORDER BY order_date ASC;
	 */

//	query()
//		.select(CustomerOrder.orderDate)
//		.from(CustomerOrder)
//		.orderBy(CustomerOrder.orderDate, Dir.ASC)

	/*
	SELECT order_date, COUNT(*)
	FROM cust_order
	ORDER BY order_date ASC;
	 */

//	query()
//		.select(CustomerOrder.orderDate, count().alias("count"))
//		.from(CustomerOrder)
//		.orderBy(CustomerOrder.orderDate, Dir.ASC)

	/*
	SELECT order_date, COUNT(*)
	FROM cust_order
	GROUP BY order_date
	ORDER BY order_date ASC;
	 */

//	query()
//		.select(CustomerOrder.orderDate, count().alias("count"))
//		.from(CustomerOrder)
//		.groupBy(CustomerOrder.orderDate)
//		.orderBy(CustomerOrder.orderDate, Dir.ASC)

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

//	query()
//		.select(all())
//		.from(CustomerOrder)

	/*
	SELECT *
	FROM order_line
	WHERE order_id = 3;
	 */

//	query()
//		.select(all())
//		.from(CustomerOrder)
//		.where(CustomerOrder.orderId eq const("3"))

	/*
	SELECT
		co.order_date AS order_day,
		COUNT(co.order_id) AS num_orders
	FROM cust_order co
		INNER JOIN order_line ol ON co.order_id = ol.order_id
	GROUP BY co.order_date
	ORDER BY co.order_date ASC;
	 */

//	val co = CustomerOrder.alias("co")
//	val ol = OrderLine.alias("ol")
//
//	query()
//		.select(
//			co.col(CustomerOrder.orderDate, "order_day"),
//			count(co.col(CustomerOrder.orderId), "num_orders")
//		)
//		.from(
//			innerJoin(co, ol, co.col(CustomerOrder.orderId) eq ol.col(OrderLine.orderId))
//		)
//		.groupBy(co.col(CustomerOrder.orderDate))
//		.orderBy(co.col(CustomerOrder.orderDate), Dir.ASC)

	/*
	SELECT
		co.*,
		ol.*
	FROM cust_order co
		INNER JOIN order_line ol ON co.order_id = ol.order_id
	WHERE co.order_date = '2018-07-10';
	 */

//	query()
//		.select(
//			co.all(),
//			ol.all()
//		)
//		.from(
//			innerJoin(co, ol, co.col(CustomerOrder.orderId) eq ol.col(CustomerOrder.orderId))
//		)
//		.where(
//			co.col(CustomerOrder.orderDate) eq const("2018-07-10")
//		)

}















