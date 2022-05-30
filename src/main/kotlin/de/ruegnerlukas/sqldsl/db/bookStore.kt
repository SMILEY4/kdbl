package de.ruegnerlukas.sqldsl.db

import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.schema.foreignKey
import de.ruegnerlukas.sqldsl.core.schema.integer
import de.ruegnerlukas.sqldsl.core.schema.primaryKey
import de.ruegnerlukas.sqldsl.core.schema.text

object Publisher : Table("publisher") {
	val publisherId = integer("publisher_id").primaryKey()
	val publisherName = text("publisher_name")
}

object BookLanguage : Table("book_language") {
	val languageId = integer("language_id").primaryKey()
	val languageCode = text("language_code")
	val languageName = text("language_name")
}

object Book : Table("book") {
	val bookId = integer("book_id").primaryKey()
	val title = text("title")
	val isbn13 = text("isbn13")
	val languageId = integer("language_id").foreignKey(BookLanguage.languageId)
	val numPages = integer("num_pages")
	val publicationDate = integer("publication_date")
	val publisherId = integer("publisher_id").foreignKey(Publisher.publisherId)
}

object Author : Table("author") {
	val authorId = integer("author_id").primaryKey()
	val authorName = text("author_name")
}

object BookAuthor : Table("book_author") {
	val bookId = integer("book_id").primaryKey().foreignKey(Book.bookId)
	val authorId = integer("author_id").primaryKey().foreignKey(Author.authorId)
}

object OrderLine : Table("order_line") {
	val lineId = integer("line_id").primaryKey()
	val orderId = integer("order_id").foreignKey(CustomerOrder.orderId)
	val bookId = integer("book_id").foreignKey(Book.bookId)
	val price = integer("price")
}

object CustomerOrder : Table("cust_order") {
	val orderId = integer("order_id").primaryKey()
	val orderDate = integer("order_date")
	val customerId = integer("customer_id").foreignKey(Customer.customerId)
	val shippingMethodId = integer("shipping_method_id").foreignKey(ShippingMethod.methodId)
	val destAddressId = integer("dest_address_id").foreignKey(Address.addressId)
}

object ShippingMethod : Table("shipping_method") {
	val methodId = integer("method_id").primaryKey()
	val methodName = text("method_name")
	val cost = integer("cost")
}

object Address : Table("address") {
	val addressId = integer("address_id").primaryKey()
	val streetNumber = text("street_number")
	val streetName = text("street_name")
	val city = text("city")
	val countryId = integer("country_id").foreignKey(Country.countryId)
}

object Country : Table("address") {
	val countryId = integer("country_id").primaryKey()
	val countryName = text("country_name")
}

object Customer : Table("customer") {
	val customerId = integer("customer_id").primaryKey()
	val firstName = text("first_name")
	val lastName = text("last_name")
	val email = text("email")
}

object CustomerAddress : Table("customer_address") {
	val customerId = integer("customer_id").primaryKey().foreignKey(Customer.customerId)
	val addressId = integer("address_id").primaryKey().foreignKey(Address.addressId)
	val statusId = integer("status_id").foreignKey(AddressStatus.statusId)
}

object AddressStatus : Table("address_status") {
	val statusId = integer("status_id").primaryKey()
	val addressStatus = text("address_status")
}

object OrderHistory : Table("order_history") {
	val historyId = integer("history_id").primaryKey()
	val orderId = integer("order_id").foreignKey(CustomerOrder.orderId)
	val statusId = integer("status_id").foreignKey(OrderStatus.statusId)
	val statusDate = integer("status_date")
}

object OrderStatus : Table("order_status") {
	val statusId = integer("status_id").primaryKey()
	val statusValue = text("status_value")
}