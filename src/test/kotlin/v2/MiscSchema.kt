package v2

import de.ruegnerlukas.sqldsl2.grammar.table.AliasTable
import de.ruegnerlukas.sqldsl2.grammar.table.TableBase
import de.ruegnerlukas.sqldsl2.schema.FloatColumn.Companion.float
import de.ruegnerlukas.sqldsl2.schema.IntColumn.Companion.integer
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.schema.TextColumn.Companion.text

object Orders : OrdersTableDef()

sealed class OrdersTableDef : Table<OrdersTableDef>("orders") {
	val orderNumber = integer("ord_no").primaryKey()
	val purchaseAmount = float("purch_amt").notNull()
	val orderDate = text("ord_date").notNull()
	val customerId = integer("customer_id").notNull()
	val salesmanId = integer("salesman_id").notNull()

	companion object {
		class OrdersTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : OrdersTableDef(), AliasTable
	}

	override fun alias(alias: String): OrdersTableDef {
		return OrdersTableDefAlias(this, alias)
	}
}


object Item : ItemTableDef()

sealed class ItemTableDef : Table<ItemTableDef>("item_mast") {
	val id = integer("pro_id").primaryKey()
	val name = text("pro_name")
	val price = float("pro_price")
	val company = integer("pro_com")

	companion object {
		class ItemTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : ItemTableDef(), AliasTable
	}

	override fun alias(alias: String): ItemTableDef {
		return ItemTableDefAlias(this, alias)
	}
}


object Sale : SaleTableDef()

sealed class SaleTableDef : Table<SaleTableDef>("sale_mast") {
	val id = integer("sale_id")
	val employeeId = integer("employee_id")
	val date = text("sale_date")
	val amount = integer("sale_amt")

	companion object {
		class SaleTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : SaleTableDef(), AliasTable
	}

	override fun alias(alias: String): SaleTableDef {
		return SaleTableDefAlias(this, alias)
	}
}


object Logs : LogsTableDef()

sealed class LogsTableDef : Table<LogsTableDef>("logs") {
	val studentId = integer("student_id")
	val marks = integer("marks")

	companion object {
		class LogsTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : LogsTableDef(), AliasTable
	}

	override fun alias(alias: String): LogsTableDef {
		return LogsTableDefAlias(this, alias)
	}
}



object Employee : EmployeesTableDef()

sealed class EmployeesTableDef : Table<EmployeesTableDef>("employees") {
	val id = integer("employee_id")
	val name = text("employee_name")
	val email = text("email_id")

	companion object {
		class EmployeesTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : EmployeesTableDef(), AliasTable
	}

	override fun alias(alias: String): EmployeesTableDef {
		return EmployeesTableDefAlias(this, alias)
	}
}


object Exam : ExamTableDef()

sealed class ExamTableDef : Table<ExamTableDef>("exam_test") {
	val id = integer("exam_id").primaryKey()
	val subject = integer("subject_id").primaryKey()
	val year = integer("exam_year").primaryKey()
	val numStudents = integer("no_of_student")

	companion object {
		class ExamTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : ExamTableDef(), AliasTable
	}

	override fun alias(alias: String): ExamTableDef {
		return ExamTableDefAlias(this, alias)
	}
}


object Subject : SubjectTableDef()

sealed class SubjectTableDef : Table<SubjectTableDef>("subject_test") {
	val id = integer("subject_id").primaryKey()
	val name = text("subject_name")

	companion object {
		class SubjectTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : SubjectTableDef(), AliasTable
	}

	override fun alias(alias: String): SubjectTableDef {
		return SubjectTableDefAlias(this, alias)
	}
}


object Sale2 : Sale2TableDef()

sealed class Sale2TableDef : Table<Sale2TableDef>("sales") {
	val companyId = integer("company_id").primaryKey()
	val q1Sale = integer("qtr1_sale")
	val q2Sale = integer("qtr2_sale")
	val q3Sale = integer("qtr3_sale")
	val q4Sale = integer("qtr4_sale")

	companion object {
		class Sale2TableDefAlias(override val baseTable: TableBase, override val aliasName: String) : Sale2TableDef(), AliasTable
	}

	override fun alias(alias: String): Sale2TableDef {
		return Sale2TableDefAlias(this, alias)
	}
}


object Sale3 : Sale3TableDef()

sealed class Sale3TableDef : Table<Sale3TableDef>("sales") {
	val transactionId = integer("TRANSACTION_ID").primaryKey()
	val salesmanId = integer("SALESMAN_ID").notNull()
	val amount = float("SALE_AMOUNT")

	companion object {
		class Sale3TableDefAlias(override val baseTable: TableBase, override val aliasName: String) : Sale3TableDef(), AliasTable
	}

	override fun alias(alias: String): Sale3TableDef {
		return Sale3TableDefAlias(this, alias)
	}
}

object Salesman : SalesmanTableDef()

sealed class SalesmanTableDef : Table<SalesmanTableDef>("salesman") {
	val id = integer("SALESMAN_ID").primaryKey()
	val name = text("SALESMAN_NAME")

	companion object {
		class SalesmanTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : SalesmanTableDef(), AliasTable
	}

	override fun alias(alias: String): SalesmanTableDef {
		return SalesmanTableDefAlias(this, alias)
	}
}