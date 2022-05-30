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
import de.ruegnerlukas.sqldsl.sqlite.SQLiteCreateTableGenerator

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

    val sqliteGenerator = SQLiteCreateTableGenerator(true)
    println("=== Sqlite ===")
    println(sqliteGenerator.build(Contacts))
    println()
    println(sqliteGenerator.build(Groups))
    println()
    println(sqliteGenerator.build(ContactGroups))

    val postgresqlGenerator = PostgreSqlCreateTableGenerator(true)
    println("=== PostgreSql ===")
    println(postgresqlGenerator.build(Contacts))
    println()
    println(postgresqlGenerator.build(Groups))
    println()
    println(postgresqlGenerator.build(ContactGroups))

}