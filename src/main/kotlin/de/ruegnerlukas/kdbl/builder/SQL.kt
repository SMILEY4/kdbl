package de.ruegnerlukas.kdbl.builder

import de.ruegnerlukas.kdbl.dsl.expression.Table
import de.ruegnerlukas.kdbl.dsl.statements.SelectElement

object SQL {

	//==================//
	//       QUERY      //
	//==================//

	private val queryBuilder = QueryBuilder.StartQueryBuilder()


	/**
	 * Select the given columns
	 */
	fun select(e: List<SelectElement>) = queryBuilder.select(e)


	/**
	 * Select the given columns
	 */
	fun select(vararg e: SelectElement) = queryBuilder.select(e.toList())


	/**
	 * Select the given (distinct) columns
	 */
	fun selectDistinct(e: List<SelectElement>) = queryBuilder.selectDistinct(e)


	/**
	 * Select the given (distinct) columns
	 */
	fun selectDistinct(vararg e: SelectElement) = queryBuilder.selectDistinct(e.toList())


	/**
	 * Select all distinct columns
	 */
	fun selectAll() = queryBuilder.selectAll()


	/**
	 * Select all distinct columns
	 */
	fun selectDistinctAll() = queryBuilder.selectDistinctAll()

	//==================//
	//   CREATE TABLE   //
	//==================//

	private val createTableBuilder = CreateTableBuilder()


	/**
	 * Create the given table
	 */
	fun create(table: Table) = createTableBuilder.create(table)


	/**
	 * Create the given table, only if it does not exist yet
	 */
	fun createIfNotExists(table: Table) = createTableBuilder.createIfNotExists(table)

	//==================//
	//      INSERT      //
	//==================//

	private val insertBuilder = InsertBuilder.StartInsertBuilder()
	private val insertItemBuilder = InsertBuilder.InsertItemBuilder()


	/**
	 * insert items into a table
	 */
	fun insert() = insertBuilder.insert()


	/**
	 * start building a single item to insert
	 */
	fun item() = insertItemBuilder.item()

	//==================//
	//      UPDATE      //
	//==================//

	private val updateBuilder = UpdateBuilder.StartUpdateBuilder()


	/**
	 * Updates rows of the given table
	 */
	fun update(table: Table) = updateBuilder.update(table)

	//==================//
	//      DELETE      //
	//==================//

	private val deleteBuilder = DeleteBuilder.StartDeleteBuilder()


	/**
	 * Delete rows of a table
	 */
	fun delete() = deleteBuilder.delete()

}
