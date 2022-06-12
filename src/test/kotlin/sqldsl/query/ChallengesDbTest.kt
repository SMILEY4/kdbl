package sqldsl.query

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.builder.addAll
import de.ruegnerlukas.sqldsl.builder.alias
import de.ruegnerlukas.sqldsl.builder.and
import de.ruegnerlukas.sqldsl.builder.asc
import de.ruegnerlukas.sqldsl.builder.assign
import de.ruegnerlukas.sqldsl.builder.count
import de.ruegnerlukas.sqldsl.builder.isEqual
import de.ruegnerlukas.sqldsl.builder.isGreaterOrEqualThan
import de.ruegnerlukas.sqldsl.builder.isGreaterThan
import de.ruegnerlukas.sqldsl.builder.isLessThan
import de.ruegnerlukas.sqldsl.builder.join
import de.ruegnerlukas.sqldsl.builder.max
import de.ruegnerlukas.sqldsl.builder.min
import de.ruegnerlukas.sqldsl.builder.sub
import de.ruegnerlukas.sqldsl.builder.sum
import de.ruegnerlukas.sqldsl.dsl.expr.AliasExpr
import de.ruegnerlukas.sqldsl.dsl.expr.DerivedTable
import org.junit.jupiter.api.Test
import sqldsl.Employee
import sqldsl.Exam
import sqldsl.Logs
import sqldsl.Sale
import sqldsl.Sale2
import sqldsl.Sale3
import sqldsl.Salesman
import sqldsl.Subject
import sqldsl.utils.assertQuery

/**
 * https://www.w3resource.com/sql-exercises/challenges-1/index.php
 */
class ChallengesDbTest {

	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-2.php
	 * SELECT MAX(sale_amt) AS second_highest_sale
	 * FROM (
	 *     SELECT DISTINCT sale_amt
	 *     FROM salemast
	 *     ORDER BY sale_amt DESC
	 *     LIMIT 2 offset 1
	 * ) AS sale;
	 */
	@Test
	fun query2_a() {
		val sales = DerivedTable("sales")
		val query = SQL
			.select(sales.column(Sale.amount).max().alias("second_highest_sale"))
			.from(
				SQL
					.selectDistinct(Sale.amount)
					.from(Sale)
					.orderBy(Sale.amount.asc())
					.limit(2, 1)
					.assign(sales)
			)
		assertQuery(
			query,
			"SELECT (MAX(sales.sale_amt)) AS second_highest_sale FROM (SELECT DISTINCT sale_mast.sale_amt FROM sale_mast ORDER BY sale_mast.sale_amt ASC LIMIT 2 OFFSET 1) AS sales"
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-2.php
	 * SELECT MAX(sale_amt) AS SecondHighestSale
	 * FROM salemast
	 * WHERE sale_amt < (SELECT MAX(sale_amt) FROM salemast);
	 */
	@Test
	fun query2_b() {
		val query = SQL
			.select(Sale.amount.max().alias("second_highest_sale"))
			.from(Sale)
			.where(Sale.amount.isLessThan(SQL.select(Sale.amount.max()).from(Sale).build()))
		assertQuery(
			query,
			"SELECT (MAX(sale_mast.sale_amt)) AS second_highest_sale FROM sale_mast WHERE sale_mast.sale_amt < (SELECT MAX(sale_mast.sale_amt) FROM sale_mast)"
		)
	}


	/**
	 * https://www.w3resource.com/sql-exercises/challenges-1/sql-challenges-1-exercise-4.php
	 * SELECT DISTINCT L1.marks AS consecutive_nums
	 * FROM (logs L1 JOIN logs L2 ON L1.marks = L2.marks AND L1.student_id = L2.student_id-1)
	 * JOIN logs L3 ON L1.marks = L3.marks AND L2.student_id = L3.student_id-1;
	 */
	@Test
	fun query4() {
		val l1 = Logs.alias("l1")
		val l2 = Logs.alias("l2")
		val l3 = Logs.alias("l3")
		val query = SQL
			.selectDistinct(l1.marks.alias("consecutive_nums"))
			.from(
				l1
					.join(l2).on(l1.marks.isEqual(l2.marks) and (l1.studentId.isEqual(l2.studentId.sub(1))))
					.join(l3).on(l1.marks.isEqual(l3.marks) and (l2.studentId.isEqual(l3.studentId.sub(1))))
			)
		assertQuery(
			query,
			"SELECT DISTINCT l1.marks AS consecutive_nums FROM ((logs AS l1) JOIN (logs AS l2) ON ((l1.marks = l2.marks) AND (l1.student_id = (l2.student_id - 1)))) JOIN (logs AS l3) ON ((l1.marks = l3.marks) AND (l2.student_id = (l3.student_id - 1)))"
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
	@Test
	fun query5() {
		val countEmail = DerivedTable("count_email").assign(
			SQL
				.select(Employee.email, Employee.email.count().alias("numOfAppearance"))
				.from(Employee)
				.groupBy(Employee.email)
		)
		val query = SQL
			.select(countEmail.column(Employee.email))
			.from(countEmail)
			.where(countEmail.columnInt("numOfAppearance").isGreaterThan(1))
		assertQuery(
			query,
			"SELECT count_email.email_id FROM (SELECT employees.email_id, (COUNT(employees.email_id)) AS numOfAppearance FROM employees GROUP BY employees.email_id) AS count_email WHERE count_email.numOfAppearance > 1"
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
	@Test
	fun query31() {
		val p = Subject.alias("p")
		val s1 = Exam.alias("s1")
		val s2 = DerivedTable("s2").assign(
			SQL
				.select(Exam.subject, Exam.year.min().alias("min_year"))
				.from(Exam)
				.groupBy(Exam.subject)
		)
		val query = SQL
			.select(s1.id, p.name, s1.year.alias("first_year"), s1.numStudents)
			.from(
				s1
					.join(p).on(s1.subject.isEqual(p.id))
					.join(s2).on(s1.subject.isEqual(s2.column(Exam.subject)) and s1.year.isEqual(s2.columnInt("min_year")))
			)
		assertQuery(
			query,
			"SELECT s1.exam_id, p.subject_name, s1.exam_year AS first_year, s1.no_of_student FROM ((exam_test AS s1) JOIN (subject_test AS p) ON (s1.subject_id = p.subject_id)) JOIN ((SELECT exam_test.subject_id, (MIN(exam_test.exam_year)) AS min_year FROM exam_test GROUP BY exam_test.subject_id) AS s2) ON ((s1.subject_id = s2.subject_id) AND (s1.exam_year = s2.min_year))"
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
	@Test
	fun query53() {
		val a = DerivedTable("a")
		val query = SQL
			.select(
				a.columnInt("total_sale").max().alias("max_sale"),
				a.columnInt("total_sale").min().alias("min_sale"),
				a.columnInt("total_sale").max().sub(a.columnInt("total_sale").min()).alias("sale_difference")
			)
			.from(
				SQL
					.select(
						Sale2.companyId,
						addAll(Sale2.q1Sale, Sale2.q2Sale, Sale2.q3Sale, Sale2.q4Sale).sum().alias("total_sale")
					)
					.from(Sale2)
					.groupBy(Sale2.companyId)
					.assign(a)
			)
		assertQuery(
			query,
			"SELECT (MAX(a.total_sale)) AS max_sale, (MIN(a.total_sale)) AS min_sale, ((MAX(a.total_sale)) - (MIN(a.total_sale))) AS sale_difference FROM (SELECT sales.company_id, (SUM(sales.qtr1_sale + sales.qtr2_sale + sales.qtr3_sale + sales.qtr4_sale)) AS total_sale FROM sales GROUP BY sales.company_id) AS a"
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
	@Test
	fun query54() {
		val totalSaleAmount = AliasExpr<Float>("total_sale_amount")
		val query = SQL
			.select(
				Salesman.id,
				Salesman.name.alias("name"),
				Sale3.transactionId.count().alias("order_count"),
				Sale3.amount.sum().alias(totalSaleAmount)
			)
			.from(Sale3.join(Salesman).on(Sale3.salesmanId.isEqual(Salesman.id)))
			.groupBy(Salesman.id, Salesman.name)
			.having(totalSaleAmount.isGreaterThan(30000F) and Sale3.amount.count().isGreaterOrEqualThan(5))
		assertQuery(
			query,
			"SELECT salesman.SALESMAN_ID, salesman.SALESMAN_NAME AS name, (COUNT(sales.TRANSACTION_ID)) AS order_count, (SUM(sales.SALE_AMOUNT)) AS total_sale_amount FROM sales JOIN salesman ON (sales.SALESMAN_ID = salesman.SALESMAN_ID) GROUP BY salesman.SALESMAN_ID, salesman.SALESMAN_NAME HAVING (total_sale_amount > 30000.0) AND ((COUNT(sales.SALE_AMOUNT)) >= 5)"
		)
	}

}