package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement

object SQL {

	//==================//
	//       QUERY      //
	//==================//

	private val queryBuilder = QueryBuilder.StartQueryBuilder()

	fun select(e: List<SelectElement>) = queryBuilder.select(e)

	fun select(vararg e: SelectElement) = queryBuilder.select(e.toList())

	fun selectDistinct(e: List<SelectElement>) = queryBuilder.selectDistinct(e)

	fun selectDistinct(vararg e: SelectElement) = queryBuilder.selectDistinct(e.toList())

	fun selectAll() = queryBuilder.selectAll()

	fun selectDistinctAll() = queryBuilder.selectDistinctAll()

	//==================//
	//   CREATE TABLE   //
	//==================//

	private val createTableBuilder = CreateTableBuilder()

	fun create(table: Table) = createTableBuilder.create(table)

	fun createIfNotExists(table: Table) = createTableBuilder.createIfNotExists(table)

	//==================//
	//      INSERT      //
	//==================//

	private val insertBuilder = InsertBuilder.StartInsertBuilder()
	private val insertItemBuilder = InsertBuilder.InsertItemBuilder()

	fun insert() = insertBuilder.insert()

	fun item() = insertItemBuilder.item()

	//==================//
	//      UPDATE      //
	//==================//

	private val updateBuilder = UpdateBuilder.StartUpdateBuilder()

	fun update(table: Table) = updateBuilder.update(table)

	//==================//
	//      DELETE      //
	//==================//

	private val deleteBuilder = DeleteBuilder.StartDeleteBuilder()

	fun delete() = deleteBuilder.delete()

}
