package sqldsl

import de.ruegnerlukas.sqldsl.dsl.expr.AliasTable
import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.expr.TableLike

object Orders : OrdersTableDef()

sealed class OrdersTableDef : Table("orders") {
	val orderNumber = integer("ord_no")
	val purchaseAmount = float("purch_amt")
	val orderDate = text("ord_date")
	val customerId = integer("customer_id")
	val salesmanId = integer("salesman_id")

	companion object {
		class OrdersTableDefAlias(override val table: TableLike, override val alias: String) : OrdersTableDef(), AliasTable
	}

	override fun alias(alias: String): OrdersTableDefAlias {
		return OrdersTableDefAlias(this, alias)
	}
}


object Item : ItemTableDef()

sealed class ItemTableDef : Table("item_mast") {
	val id = integer("pro_id")
	val name = text("pro_name")
	val price = float("pro_price")
	val company = integer("pro_com")

	companion object {
		class ItemTableDefAlias(override val table: TableLike, override val alias: String) : ItemTableDef(), AliasTable
	}

	override fun alias(alias: String): ItemTableDefAlias {
		return ItemTableDefAlias(this, alias)
	}
}


object Sale : SaleTableDef()

sealed class SaleTableDef : Table("sale_mast") {
	val id = integer("sale_id")
	val employeeId = integer("employee_id")
	val date = text("sale_date")
	val amount = integer("sale_amt")

	companion object {
		class SaleTableDefAlias(override val table: TableLike, override val alias: String) : SaleTableDef(), AliasTable
	}

	override fun alias(alias: String): SaleTableDefAlias {
		return SaleTableDefAlias(this, alias)
	}
}


object Logs : LogsTableDef()

sealed class LogsTableDef : Table("logs") {
	val studentId = integer("student_id")
	val marks = integer("marks")

	companion object {
		class LogsTableDefAlias(override val table: TableLike, override val alias: String) : LogsTableDef(), AliasTable
	}

	override fun alias(alias: String): LogsTableDefAlias {
		return LogsTableDefAlias(this, alias)
	}
}


object Employee : EmployeesTableDef()

sealed class EmployeesTableDef : Table("employees") {
	val id = integer("employee_id")
	val name = text("employee_name")
	val email = text("email_id")

	companion object {
		class EmployeesTableDefAlias(override val table: TableLike, override val alias: String) : EmployeesTableDef(), AliasTable
	}

	override fun alias(alias: String): EmployeesTableDefAlias {
		return EmployeesTableDefAlias(this, alias)
	}
}


object Exam : ExamTableDef()

sealed class ExamTableDef : Table("exam_test") {
	val id = integer("exam_id").primaryKey()
	val subject = integer("subject_id").primaryKey()
	val year = integer("exam_year").primaryKey()
	val numStudents = integer("no_of_student")

	companion object {
		class ExamTableDefAlias(override val table: TableLike, override val alias: String) : ExamTableDef(), AliasTable
	}

	override fun alias(alias: String): ExamTableDefAlias {
		return ExamTableDefAlias(this, alias)
	}
}


object Subject : SubjectTableDef()

sealed class SubjectTableDef : Table("subject_test") {
	val id = integer("subject_id")
	val name = text("subject_name")

	companion object {
		class SubjectTableDefAlias(override val table: TableLike, override val alias: String) : SubjectTableDef(), AliasTable
	}

	override fun alias(alias: String): SubjectTableDefAlias {
		return SubjectTableDefAlias(this, alias)
	}
}


object Sale2 : Sale2TableDef()

sealed class Sale2TableDef : Table("sales") {
	val companyId = integer("company_id")
	val q1Sale = integer("qtr1_sale")
	val q2Sale = integer("qtr2_sale")
	val q3Sale = integer("qtr3_sale")
	val q4Sale = integer("qtr4_sale")

	companion object {
		class Sale2TableDefAlias(override val table: TableLike, override val alias: String) : Sale2TableDef(), AliasTable
	}

	override fun alias(alias: String): Sale2TableDefAlias {
		return Sale2TableDefAlias(this, alias)
	}
}


object Sale3 : Sale3TableDef()

sealed class Sale3TableDef : Table("sales") {
	val transactionId = integer("TRANSACTION_ID")
	val salesmanId = integer("SALESMAN_ID")
	val amount = float("SALE_AMOUNT")

	companion object {
		class Sale3TableDefAlias(override val table: TableLike, override val alias: String) : Sale3TableDef(), AliasTable
	}

	override fun alias(alias: String): Sale3TableDefAlias {
		return Sale3TableDefAlias(this, alias)
	}
}

object Salesman : SalesmanTableDef()

sealed class SalesmanTableDef : Table("salesman") {
	val id = integer("SALESMAN_ID")
	val name = text("SALESMAN_NAME")

	companion object {
		class SalesmanTableDefAlias(override val table: TableLike, override val alias: String) : SalesmanTableDef(), AliasTable
	}

	override fun alias(alias: String): SalesmanTableDefAlias {
		return SalesmanTableDefAlias(this, alias)
	}
}
