package de.ruegnerlukas.kdbl.dsl.expression

import de.ruegnerlukas.kdbl.dsl.statements.FromElement
import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime

/**
 * A table or sth directly referencing a single table (i.e. a table-alias)
 */
sealed interface TableLike : FromElement


/**
 * A table with an alias
 */
interface AliasTable : TableLike {
	val table: TableLike
	val alias: String
}


/**
 * A table
 */
abstract class Table(val tableName: String, private val defaultNotNullColumns: Boolean = false) : TableLike {

	private val columns = mutableListOf<Column<*>>()

	fun getColumns() = columns.toList()

	private fun register(column: Column<*>) {
		if (defaultNotNullColumns) {
			column.notNull()
		}
		columns.add(column)
	}

	protected fun boolean(name: String) = Column<Boolean>(this, name, DataType.BOOL).apply { register(this) }
	protected fun smallint(name: String) = Column<Short>(this, name, DataType.SMALLINT).apply { register(this) }
	protected fun integer(name: String) = Column<Int>(this, name, DataType.INT).apply { register(this) }
	protected fun bigint(name: String) = Column<Long>(this, name, DataType.BIGINT).apply { register(this) }
	protected fun float(name: String) = Column<Float>(this, name, DataType.FLOAT).apply { register(this) }
	protected fun double(name: String) = Column<Double>(this, name, DataType.DOUBLE).apply { register(this) }
	protected fun text(name: String) = Column<String>(this, name, DataType.TEXT).apply { register(this) }
	protected fun date(name: String) = Column<SqlDate>(this, name, DataType.DATE).apply { register(this) }
	protected fun time(name: String) = Column<SqlTime>(this, name, DataType.TIME).apply { register(this) }
	protected fun timestamp(name: String) = Column<Long>(this, name, DataType.TIMESTAMP).apply { register(this) }

	abstract fun alias(alias: String): AliasTable
}


/**
 * A derived table, e.g. the result from a sub-query or a join
 */
class DerivedTable(val tableName: String) : TableLike {

	private var content: FromElement? = null


	/**
	 * Assign the given content to this table
	 */
	fun assign(content: FromElement): DerivedTable {
		this.content = content
		return this
	}


	/**
	 * @return the assigned content
	 */
	fun getContent(): FromElement {
		return this.content ?: throw IllegalStateException("No content assigned to derived table '$tableName'")
	}

	fun columnBoolean(columnName: String): DerivedColumn<Boolean> = DerivedColumn(this, columnName)
	fun columnSmallint(columnName: String): DerivedColumn<Short> = DerivedColumn(this, columnName)
	fun columnInt(columnName: String): DerivedColumn<Int> = DerivedColumn(this, columnName)
	fun columnBigint(columnName: String): DerivedColumn<Long> = DerivedColumn(this, columnName)
	fun columnFloat(columnName: String): DerivedColumn<Float> = DerivedColumn(this, columnName)
	fun columnDouble(columnName: String): DerivedColumn<Double> = DerivedColumn(this, columnName)
	fun columnText(columnName: String): DerivedColumn<String> = DerivedColumn(this, columnName)
	fun columnDate(columnName: String): DerivedColumn<SqlDate> = DerivedColumn(this, columnName)
	fun columnTime(columnName: String): DerivedColumn<SqlTime> = DerivedColumn(this, columnName)
	fun columnTimestamp(columnName: String): DerivedColumn<Long> = DerivedColumn(this, columnName)

	fun <T> column(columnExpr: Column<T>): DerivedColumn<T> = DerivedColumn(this, columnExpr.columnName)

}


