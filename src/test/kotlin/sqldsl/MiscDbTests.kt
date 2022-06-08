package sqldsl

import de.ruegnerlukas.sqldsl.builders.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.builders.SQL
import de.ruegnerlukas.sqldsl.builders.alias
import de.ruegnerlukas.sqldsl.builders.avg
import de.ruegnerlukas.sqldsl.builders.countAll
import de.ruegnerlukas.sqldsl.builders.div
import de.ruegnerlukas.sqldsl.builders.isEqual
import de.ruegnerlukas.sqldsl.builders.isGreaterOrEqualThan
import de.ruegnerlukas.sqldsl.builders.isGreaterThan
import de.ruegnerlukas.sqldsl.builders.max
import de.ruegnerlukas.sqldsl.builders.mul
import de.ruegnerlukas.sqldsl.builders.sub
import de.ruegnerlukas.sqldsl.builders.sum
import de.ruegnerlukas.sqldsl.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.grammar.expr.AvgAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl.grammar.expr.FloatLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MiscDbTests {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryBuilderEndStep<*>, expected: String) {
		val strQuery = generator.buildString(query.build())
		println(strQuery)
		assertEquals(expected, strQuery)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-boolean-operator-exercise-10.php
	 * SELECT ord_no,purch_amt,
	 * (100*purch_amt)/6000 AS "Achieved %",
	 * (100*(6000-purch_amt)/6000) AS "Unachieved %"
	 * FROM  orders
	 * WHERE (100*purch_amt)/6000>50;
	 */
	@Test
	fun query1() {
		val query = SQL
			.select(
				Orders.orderNumber,
				Orders.purchaseAmount,
				Orders.purchaseAmount.mul(100F).div(6000F).alias("archived_perc"),
				6000F.sub(Orders.purchaseAmount).mul(100F).div(6000F).alias("unarchived_perc")
			)
			.from(Orders)
			.where(Orders.purchaseAmount.mul(100F).div(6000F).isGreaterThan(50F))
		assertQuery(
			query,
			"SELECT orders.ord_no, orders.purch_amt, (((orders.purch_amt) * (100.0)) / (6000.0)) AS archived_perc, ((((6000.0) - (orders.purch_amt)) * (100.0)) / (6000.0)) AS unarchived_perc FROM orders WHERE (((orders.purch_amt) * (100.0)) / (6000.0)) > (50.0)"
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-1.php
	 * SELECT SUM(purch_amt)
	 * FROM orders;
	 */
	@Test
	fun query2() {
		val query = SQL
			.select(Orders.purchaseAmount.sum())
			.from(Orders)
		assertQuery(query, "SELECT SUM(orders.purch_amt) FROM orders")
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-11.php
	 * SELECT salesman_id, MAX(purch_amt)
	 * FROM orders
	 * WHERE ord_date = '2012-08-17'
	 * GROUP BY salesman_id;
	 */
	@Test
	fun query3() {
		val query = SQL
			.select(Orders.salesmanId, Orders.purchaseAmount.max())
			.from(Orders)
			.where(Orders.orderDate.isEqual("2012-08-17"))
			.groupBy(Orders.salesmanId)
		assertQuery(
			query,
			"SELECT orders.salesman_id, MAX(orders.purch_amt) FROM orders WHERE (orders.ord_date) = ('2012-08-17') GROUP BY orders.salesman_id"
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-12.php
	 * SELECT customer_id,ord_date,MAX(purch_amt)
	 * FROM orders
	 * GROUP BY customer_id,ord_date
	 * HAVING MAX(purch_amt)>2000.00;
	 */
	@Test
	fun query4() {
		val query = SQL
			.select(Orders.customerId, Orders.orderDate, Orders.purchaseAmount.max())
			.from(Orders)
			.groupBy(Orders.customerId, Orders.orderDate)
			.having(Orders.purchaseAmount.max().isGreaterThan(2000F))
		assertQuery(
			query,
			"SELECT orders.customer_id, orders.ord_date, MAX(orders.purch_amt) FROM orders GROUP BY orders.customer_id, orders.ord_date HAVING (MAX(orders.purch_amt)) > (2000.0)"
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-22.php
	 * SELECT COUNT(*) AS "Number of Products"
	 * FROM item_mast
	 * WHERE pro_price >= 350;
	 */
	@Test
	fun query5() {
		val query = SQL
			.select(countAll().alias("num_products"))
			.from(Item)
			.where(Item.price.isGreaterOrEqualThan(350F))
		assertQuery(query, "SELECT (COUNT(*)) AS num_products FROM item_mast WHERE (item_mast.pro_price) >= (350.0)")
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-23.php
	 * SELECT AVG(pro_price) AS "Average Price", pro_com AS "Company ID"
	 * FROM item_mast
	 * GROUP BY pro_com;
	 */
	@Test
	fun query6() {
		val query = SQL
			.select(Item.price.avg().alias("avg_price"), Item.company.alias("company_id"))
			.from(Item)
			.groupBy(Item.company)
		assertQuery(
			query,
			"SELECT (AVG(item_mast.pro_price)) AS avg_price, (item_mast.pro_com) AS company_id FROM item_mast GROUP BY item_mast.pro_com"
		)
	}

	@Test
	fun createTable() {
		SQL.createTable(Movie)
		TODO()
	}

}