package de.ruegnerlukas.sqldsl.dsl.expr

import de.ruegnerlukas.sqldsl.dsl.statements.FromElement

interface TableLike : FromElement

interface AliasTable : TableLike {
	val table: TableLike
	val alias: String
}

abstract class Table(val tableName: String) : TableLike {

	private val columns = mutableListOf<Column<*>>()

	fun getColumns() = columns.toList()

	protected fun integer(name: String) = Column<Int>(this, name, ColumnType.INT).apply { columns.add(this) }
	protected fun float(name: String) = Column<Float>(this, name, ColumnType.FLOAT).apply { columns.add(this) }
	protected fun text(name: String) = Column<String>(this, name, ColumnType.TEXT).apply { columns.add(this) }
	protected fun boolean(name: String) = Column<Boolean>(this, name, ColumnType.BOOL).apply { columns.add(this) }

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

	fun columnInt(columnName: String): DerivedColumn<Int> {
		return DerivedColumn(this, columnName)
	}

	fun columnFloat(columnName: String): DerivedColumn<Float> {
		return DerivedColumn(this, columnName)
	}

	fun columnString(columnName: String): DerivedColumn<String> {
		return DerivedColumn(this, columnName)
	}

	fun columnBool(columnName: String): DerivedColumn<Boolean> {
		return DerivedColumn(this, columnName)
	}

	fun <T> column(columnExpr: Column<T>): DerivedColumn<T> {
		return DerivedColumn(this, columnExpr.columnName)
	}

}


