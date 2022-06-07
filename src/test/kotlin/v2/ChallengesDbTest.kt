package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AddAllOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.CountAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.LessThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.MaxAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.MinAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.SubOperation
import de.ruegnerlukas.sqldsl2.grammar.expr.SumAggFunction
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.limit.LimitStatement
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectDistinctStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.table.DerivedTable
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

fun main() {
	ChallengesDbTest().all()
}


/**
 * https://www.w3resource.com/sql-exercises/challenges-1/index.php
 */
class ChallengesDbTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("2_a", query2_a())
		printQuery("2_b", query2_b())
		printQuery("4", query4())
		printQuery("5", query5())
		printQuery("31", query31())
		printQuery("53", query53())
		printQuery("54", query54())
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
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-2.php
	 * SELECT MAX(sale_amt) AS SecondHighestSale
	 * FROM (SELECT DISTINCT sale_amt FROM salemast ORDER BY sale_amt DESC LIMIT 2 offset 1) AS sale;
	 */
	fun query2_a(): QueryStatement {
		val sales = DerivedTable("sales")
		return QueryStatement(
			select = SelectStatement(
				listOf(
					AliasColumn(MaxAggFunction(sales.columnInt(Sale.amount)), "second_highest_sale")
				)
			),
			from = FromStatement(
				listOf(
					sales.assign(
						QueryStatement(
							select = SelectDistinctStatement(
								listOf(
									Sale.amount
								)
							),
							from = FromStatement(
								listOf(
									Sale
								)
							),
							orderBy = OrderByStatement(
								listOf(
									OrderByExpression(Sale.amount, Dir.ASC)
								)
							),
							limit = LimitStatement(2, 1)
						)
					)
				)
			)
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-2.php
	 * SELECT MAX(sale_amt) AS SecondHighestSale
	 * FROM salemast
	 * WHERE sale_amt < (SELECT MAX(sale_amt) FROM salemast);
	 */
	fun query2_b(): QueryStatement {
		return QueryStatement(
			select = SelectStatement(
				listOf(
					AliasColumn(MaxAggFunction(Sale.amount), "second_highest_sale")
				)
			),
			from = FromStatement(
				listOf(
					Sale
				)
			),
			where = WhereStatement(
				LessThanCondition(
					Sale.amount,
					QueryStatement(
						from = FromStatement(listOf(Sale)),
						select = SelectStatement(listOf(MaxAggFunction(Sale.amount)))
					)
				)
			)
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-4.php
	 * SELECT DISTINCT L1.marks AS  ConsecutiveNums
	 * FROM (logs L1 JOIN logs L2 ON L1.marks = L2.marks AND L1.student_id = L2.student_id-1)
	 * JOIN logs L3 ON L1.marks = L3.marks AND L2.student_id = L3.student_id-1;
	 */
	fun query4(): QueryStatement {
		val l1 = Logs.alias("l1")
		val l2 = Logs.alias("l2")
		val l3 = Logs.alias("l3")
		return QueryStatement(
			select = SelectDistinctStatement(
				listOf(
					AliasColumn(l1.marks, "consecutive_nums")
				)
			),
			from = FromStatement(
				listOf(
					JoinClause(
						JoinOp.LEFT,
						JoinClause(
							JoinOp.LEFT,
							l1,
							l2,
							ConditionJoinConstraint(
								AndCondition(
									EqualCondition(l1.marks, l2.marks),
									EqualCondition(
										l1.studentId, SubOperation(
											l2.studentId,
											IntLiteral(1)
										)
									)
								)
							)
						),
						l3,
						ConditionJoinConstraint(
							AndCondition(
								EqualCondition(l1.marks, l3.marks),
								EqualCondition(
									l2.studentId, SubOperation(
										l3.studentId,
										IntLiteral(1)
									)
								)
							)
						)
					)
				)
			)
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-5.php
	 * SELECT email_id
	 * FROM
	 * (
	 * 	  SELECT email_id, COUNT(email_id) AS nuOfAppearence
	 * 	  FROM employees
	 * 	  GROUP BY email_id
	 * ) AS countEmail
	 * WHERE nuOfAppearence> 1;
	 */
	fun query5(): QueryStatement {
		val countEmail = DerivedTable("count_email").assign(
			QueryStatement(
				select = SelectStatement(
					listOf(
						Employee.email,
						AliasColumn(CountAggFunction(Employee.email), "numOfAppearance")
					)
				),
				from = FromStatement(
					listOf(
						Employee
					)
				),
				groupBy = GroupByStatement(
					listOf(
						Employee.email
					)
				)
			)
		)
		return QueryStatement(
			select = SelectStatement(
				listOf(
					countEmail.columnInt(Employee.email)
				)
			),
			from = FromStatement(
				listOf(
					countEmail
				)
			),
			where = WhereStatement(
				GreaterThanCondition(
					countEmail.columnInt("numOfAppearance"),
					IntLiteral(1)
				)
			)
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-31.php
	 * SELECT s1.exam_id, p.subject_name, s1.exam_year as first_year, s1.no_of_student
	 * FROM exam_test s1
	 * 	  JOIN subject_test p on s1.subject_id = p.subject_id
	 * 	     JOIN (
	 * 	        SELECT subject_id, min(exam_year) min_yr
	 * 	        FROM exam_test
	 * 	        GROUP BY subject_id
	 * 	     ) s2 on s1.subject_id = s2.subject_id and s1.exam_year = s2.min_yr;
	 */
	fun query31(): QueryStatement {
		val p = Subject.alias("p")
		val s1 = Exam.alias("s1")
		val s2 = DerivedTable("s2").assign(
			QueryStatement(
				select = SelectStatement(
					listOf(
						Exam.subject,
						AliasColumn(MinAggFunction(Exam.year), "min_year")
					)
				),
				from = FromStatement(
					listOf(
						Exam
					)
				),
				groupBy = GroupByStatement(
					listOf(
						Exam.subject
					)
				)
			)
		)
		return QueryStatement(
			select = SelectStatement(
				listOf(
					s1.id,
					p.name,
					AliasColumn(s1.year, "first_year"),
					s1.numStudents
				)
			),
			from = FromStatement(
				listOf(
					JoinClause(
						JoinOp.LEFT,
						JoinClause(
							JoinOp.LEFT,
							s1,
							p,
							ConditionJoinConstraint(
								EqualCondition(
									s1.subject,
									p.id
								)
							)
						),
						s2,
						ConditionJoinConstraint(
							AndCondition(
								EqualCondition(s1.subject, s2.columnInt(Exam.subject)),
								EqualCondition(s1.year, s2.columnInt("min_year"))
							)
						)
					)
				)
			)
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-53.php
	 * SELECT
	 *    MAX(total_sale) AS max_sale,
	 *    MIN(total_sale) AS min_sale,
	 *    MAX(total_sale)-MIN(total_sale) AS sale_difference
	 * FROM
	 * (
	 *    SELECT
	 *       company_id,
	 *       sum(qtr1_sale+qtr2_sale+qtr3_sale+qtr4_sale) AS total_sale
	 *    FROM sales
	 *    GROUP BY company_id
	 * ) a;
	 */
	fun query53(): QueryStatement {
		val a = DerivedTable("a")
		return QueryStatement(
			select = SelectStatement(
				listOf(
					AliasColumn(MaxAggFunction(a.columnInt("total_sale")), "max_sale"),
					AliasColumn(MinAggFunction(a.columnInt("total_sale")), "min_sale"),
					AliasColumn(
						SubOperation(
							MaxAggFunction(a.columnInt("total_sale")),
							MinAggFunction(a.columnInt("total_sale"))
						), "sale_difference"
					)
				)
			),
			from = FromStatement(
				listOf(
					a.assign(
						QueryStatement(
							select = SelectStatement(
								listOf(
									Sale2.companyId,
									AliasColumn(
										SumAggFunction(AddAllOperation(listOf(Sale2.q1Sale, Sale2.q2Sale, Sale2.q3Sale, Sale2.q4Sale))),
										"total_sale"
									)
								)
							),
							from = FromStatement(
								listOf(
									Sale2
								)
							),
							groupBy = GroupByStatement(
								listOf(
									Sale2.companyId
								)
							)
						)
					)
				)
			)
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-54.php
	 * SELECT
	 *    salesman.salesman_id,
	 *    salesman.salesman_name AS name,
	 *    COUNT(sales.transaction_id) AS order_count,
	 *    sum(sale_amount) AS total_sale_amount
	 * FROM sales INNER JOIN salesman ON sales.salesman_id = salesman.salesman_id
	 * GROUP BY salesman.salesman_id, salesman.salesman_name
	 * HAVING total_sale_amount>30000 AND COUNT(sales.transaction_id) >=5;
	 */
	fun query54(): QueryStatement {
		val totalSaleAmount = AliasColumn("total_sale_amount")
		return QueryStatement(
			select = SelectStatement(
				listOf(
					Salesman.id,
					AliasColumn(Salesman.name, "name"),
					AliasColumn(CountAggFunction(Sale3.transactionId), "order_count"),
					totalSaleAmount.assign(SumAggFunction(Sale3.amount))
				)
			),
			from = FromStatement(
				listOf(
					JoinClause(
						JoinOp.INNER,
						Sale3,
						Salesman,
						ConditionJoinConstraint(
							EqualCondition(
								Sale3.salesmanId,
								Salesman.id
							)
						)
					)
				)
			),
			groupBy = GroupByStatement(
				listOf(
					Salesman.id,
					Salesman.name
				)
			),
			having = HavingStatement(
				AndCondition(
					GreaterThanCondition(
						totalSaleAmount,
						IntLiteral(30000)
					),
					GreaterOrEqualThanCondition(
						CountAggFunction(Sale3.amount),
						IntLiteral(5)
					)
				)
			)
		)

	}
}