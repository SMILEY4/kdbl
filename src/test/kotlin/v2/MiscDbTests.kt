package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.AvgAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.DivOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.FloatLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.MulOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.SubOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class MiscDbTests {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	private fun assertQuery(query: QueryStatement, expected: String) {
		val strQuery = generator.buildString(query)
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
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Orders.orderNumber,
					Orders.purchaseAmount,
					AliasColumn(
						DivOperation(
							MulOperation(
								FloatLiteral(100F),
								Orders.purchaseAmount
							),
							FloatLiteral(6000F)
						),
						"archived_perc"
					),
					AliasColumn(
						DivOperation(
							MulOperation(
								FloatLiteral(100F),
								SubOperation(
									FloatLiteral(6000F),
									Orders.purchaseAmount
								)
							),
							FloatLiteral(6000F)
						),
						"unarchived_perc"
					)
				)
			),
			from = FromStatement(
				listOf(
					Orders
				)
			),
			where = WhereStatement(
				GreaterThanCondition(
					DivOperation(
						MulOperation(
							FloatLiteral(100F),
							Orders.purchaseAmount
						),
						FloatLiteral(6000F)
					),
					FloatLiteral(50F)
				)
			)
		)
		assertQuery(
			query,
			"SELECT orders.ord_no, orders.purch_amt, (((100.0) * (orders.purch_amt)) / (6000.0)) AS archived_perc, (((100.0) * ((6000.0) - (orders.purch_amt))) / (6000.0)) AS unarchived_perc FROM orders WHERE (((100.0) * (orders.purch_amt)) / (6000.0)) > (50.0)"
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-1.php
	 * SELECT SUM (purch_amt)
	 * FROM orders;
	 */
	@Test
	fun query2() {
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					SumAggFunction(Orders.purchaseAmount)
				)
			),
			from = FromStatement(
				listOf(
					Orders
				)
			)
		)
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
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Orders.salesmanId,
					MaxAggFunction(Orders.purchaseAmount)
				)
			),
			from = FromStatement(
				listOf(
					Orders
				)
			),
			where = WhereStatement(
				EqualCondition(
					Orders.orderDate,
					StringLiteral("2012-08-17")
				)
			),
			groupBy = GroupByStatement(
				listOf(
					Orders.salesmanId
				)
			)
		)
		assertQuery(
			query, "SELECT orders.salesman_id, MAX(orders.purch_amt) FROM orders WHERE (orders.ord_date) = ('2012-08-17') GROUP BY orders.salesman_id"
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
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					Orders.customerId,
					Orders.orderDate,
					MaxAggFunction(Orders.purchaseAmount)
				)
			),
			from = FromStatement(
				listOf(
					Orders
				)
			),
			groupBy = GroupByStatement(
				listOf(
					Orders.customerId,
					Orders.orderDate
				)
			),
			having = HavingStatement(
				GreaterThanCondition(
					MaxAggFunction(Orders.purchaseAmount),
					FloatLiteral(2000F)
				)
			)
		)
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
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					AliasColumn(CountAllAggFunction(), "num_products")
				)
			),
			from = FromStatement(
				listOf(
					Item
				)
			),
			where = WhereStatement(
				GreaterOrEqualThanCondition(
					Item.price,
					FloatLiteral(350F)
				)
			)
		)
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
		val query = QueryStatement(
			select = SelectStatement(
				listOf(
					AliasColumn(AvgAggFunction(Item.price), "avg_price"),
					Item.company.alias("company_id")
				)
			),
			from = FromStatement(
				listOf(
					Item
				)
			),
			groupBy = GroupByStatement(
				listOf(
					Item.company
				)
			)
		)
		assertQuery(
			query,
			"SELECT (AVG(item_mast.pro_price)) AS avg_price, (item_mast.pro_com) AS company_id FROM item_mast GROUP BY item_mast.pro_com"
		)
	}
}