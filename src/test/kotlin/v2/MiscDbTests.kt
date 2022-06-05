package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AvgAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.CountAllAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.DivOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.MulOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.SubOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

fun main() {
	MiscDbTests().all()
}


class MiscDbTests {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", query1())
		printQuery("2", query2())
		printQuery("3", query3())
		printQuery("4", query4())
		printQuery("5", query5())
		printQuery("6", query6())
	}


	private fun printQuery(name: String, query: QueryStatement?) {
		println("--QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println("$str;")
		} else {
			println("--")
		}
		println()
	}


	/**
	 * https://www.w3resource.com/sql-exercises/sql-boolean-operator-exercise-10.php
	 * SELECT ord_no,purch_amt,
	 * (100*purch_amt)/6000 AS "Achieved %",
	 * (100*(6000-purch_amt)/6000) AS "Unachieved %"
	 * FROM  orders
	 * WHERE (100*purch_amt)/6000>50;
	 */
	private fun query1() = QueryStatement(
		select = SelectStatement(
			listOf(
				Orders.orderNumber,
				Orders.purchaseAmount,
				AliasSelectExpression(
					DivOperation(
						MulOperation(
							IntLiteral(100),
							Orders.purchaseAmount
						),
						IntLiteral(6000)
					),
					"archived_perc"
				),
				AliasSelectExpression(
					DivOperation(
						MulOperation(
							IntLiteral(100),
							SubOperation(
								IntLiteral(6000),
								Orders.purchaseAmount
							)
						),
						IntLiteral(6000)
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
						IntLiteral(100),
						Orders.purchaseAmount
					),
					IntLiteral(6000)
				),
				IntLiteral(50)
			)
		)
	)


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-1.php
	 * SELECT SUM (purch_amt)
	 * FROM orders;
	 */
	private fun query2() = QueryStatement(
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


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-11.php
	 * SELECT salesman_id, MAX(purch_amt)
	 * FROM orders
	 * WHERE ord_date = '2012-08-17'
	 * GROUP BY salesman_id;
	 */
	private fun query3() = QueryStatement(
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


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-12.php
	 * SELECT customer_id,ord_date,MAX(purch_amt)
	 * FROM orders
	 * GROUP BY customer_id,ord_date
	 * HAVING MAX(purch_amt)>2000.00;
	 */
	private fun query4() = QueryStatement(
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
				IntLiteral(2000)
			)
		)
	)


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-22.php
	 * SELECT COUNT(*) AS "Number of Products"
	 * FROM item_mast
	 * WHERE pro_price >= 350;
	 */
	private fun query5() = QueryStatement(
		select = SelectStatement(
			listOf(
				AliasSelectExpression(CountAllAggFunction(), "num_products")
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
				IntLiteral(350)
			)
		)
	)


	/**
	 * https://www.w3resource.com/sql-exercises/sql-aggregate-function-exercise-23.php
	 * SELECT AVG(pro_price) AS "Average Price", pro_com AS "Company ID"
	 * FROM item_mast
	 * GROUP BY pro_com;
	 */
	private fun query6() = QueryStatement(
		select = SelectStatement(
			listOf(
				AliasSelectExpression(AvgAggFunction(Item.price), "avg_price"),
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

}