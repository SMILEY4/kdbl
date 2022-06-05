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