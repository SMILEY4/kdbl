import de.ruegnerlukas.sqldsl.core.actions.insert.insertInto
import de.ruegnerlukas.sqldsl.core.schema.Create
import de.ruegnerlukas.sqldsl.core.schema.OnDelete
import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.schema.autoIncrement
import de.ruegnerlukas.sqldsl.core.schema.boolean
import de.ruegnerlukas.sqldsl.core.schema.default
import de.ruegnerlukas.sqldsl.core.schema.foreignKey
import de.ruegnerlukas.sqldsl.core.schema.integer
import de.ruegnerlukas.sqldsl.core.schema.notNull
import de.ruegnerlukas.sqldsl.core.schema.primaryKey
import de.ruegnerlukas.sqldsl.core.schema.text
import de.ruegnerlukas.sqldsl.core.schema.unique
import de.ruegnerlukas.sqldsl.postgresql.PostgreSqlCreateTableGenerator
import de.ruegnerlukas.sqldsl.postgresql.PostgreSqlInsertIntoGenerator
import de.ruegnerlukas.sqldsl.sqlite.SQLiteCreateTableGenerator
import de.ruegnerlukas.sqldsl.sqlite.SQLiteInsertIntoGenerator

object Contacts : Table("contacts", Create.IF_NOT_EXISTS) {
	val contactId = integer("contact_id").primaryKey()
	val firstName = text("first_name").notNull().default("Max")
	val lastName = text("last_name").notNull().default("Mustermann")
	val email = text("email").notNull().unique()
	val phone = text("phone").notNull().unique()
	val verified = boolean("verified").notNull().default(false)
}

object Groups : Table("groups", Create.IF_NOT_EXISTS) {
	val groupId = integer("group_id").primaryKey()
	val groupIndex = integer("group_index").autoIncrement()
	val name = text("name").notNull()
}

object ContactGroups : Table("contact_groups", Create.IF_NOT_EXISTS) {
	val contactId = integer("contact_id").primaryKey().foreignKey(Contacts.contactId, OnDelete.CASCADE)
	val groupId = integer("contact_id").primaryKey().foreignKey(Groups.groupId, OnDelete.CASCADE)
}


fun main() {

	val stmtInsert = insertInto(Contacts)
		.orIgnore()
		.columns(Contacts.contactId, Contacts.email, Contacts.phone)
		.item {
			set(Contacts.contactId, 1)
			set(Contacts.email, "example@mail.test")
			set(Contacts.phone, "+49 12345")
		}
		.item {
			set(Contacts.contactId, 2)
			set(Contacts.email, "another@example.mail")
			set(Contacts.phone, "+0123 54321")
		}
		.build()

//
//	query {
//
//		val contacts = table(Contacts)
//		val groups = table(Groups, "contactGroups")
//
//		from(
//			contacts,
//			groups
//		)
//		select(
//			contacts.column(Contacts.email),
//			groups.column(Groups.name, "groupName")
//		)
//
//		or(
//			and(
//				column(contacts.column(Contacts.email)).eq(const("free_shipping")),
//				column("value") eq const("yes")
//			),
//			and(
//				column("key") eq const("price"),
//				column("value") eq const("5")
//			)
//		)
//	}





	println("\n\n\n\n=== Sqlite ===")

	val sqliteTableGenerator = SQLiteCreateTableGenerator(true)
	println(sqliteTableGenerator.build(Contacts))
	println()
	println(sqliteTableGenerator.build(Groups))
	println()
	println(sqliteTableGenerator.build(ContactGroups))
	println()

	val sqliteInsertGenerator = SQLiteInsertIntoGenerator()
	println(sqliteInsertGenerator.build(stmtInsert))


	println("\n\n\n\n=== PostgreSql ===")

	val postgresqlGenerator = PostgreSqlCreateTableGenerator(true)
	println(postgresqlGenerator.build(Contacts))
	println()
	println(postgresqlGenerator.build(Groups))
	println()
	println(postgresqlGenerator.build(ContactGroups))
	println()

	val postgresInsertGenerator = PostgreSqlInsertIntoGenerator()
	println(postgresInsertGenerator.build(stmtInsert))

}