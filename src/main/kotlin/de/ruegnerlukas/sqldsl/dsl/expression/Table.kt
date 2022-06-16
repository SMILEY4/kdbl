package de.ruegnerlukas.sqldsl.dsl.expression

import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime

interface TableLike : FromElement

interface AliasTable : TableLike {
	val table: TableLike
	val alias: String
}

abstract class Table(val tableName: String) : TableLike {

	private val columns = mutableListOf<Column<*>>()

	fun getColumns() = columns.toList()

	protected fun boolean(name: String) = Column<Boolean>(this, name, DataType.BOOL).apply { columns.add(this) }
	protected fun smallint(name: String) = Column<Short>(this, name, DataType.SMALLINT).apply { columns.add(this) }
	protected fun integer(name: String) = Column<Int>(this, name, DataType.INT).apply { columns.add(this) }
	protected fun bigint(name: String) = Column<Long>(this, name, DataType.BIGINT).apply { columns.add(this) }
	protected fun float(name: String) = Column<Float>(this, name, DataType.FLOAT).apply { columns.add(this) }
	protected fun double(name: String) = Column<Double>(this, name, DataType.DOUBLE).apply { columns.add(this) }
	protected fun text(name: String) = Column<String>(this, name, DataType.TEXT).apply { columns.add(this) }
	protected fun date(name: String) = Column<SqlDate>(this, name, DataType.DATE).apply { columns.add(this) }
	protected fun time(name: String) = Column<SqlTime>(this, name, DataType.TIME).apply { columns.add(this) }
	protected fun timestamp(name: String) = Column<Long>(this, name, DataType.TIMESTAMP).apply { columns.add(this) }

	abstract fun alias(alias: String): AliasTable
}


/**
 * A derived table, e.g. the result from a sub-query or a join
 */
class DerivedTable(val tableName: String) : TableLike {

	private var content: FromElement? = null

	fun assign(content: FromElement): DerivedTable {
		this.content = content
		return this
	}

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


