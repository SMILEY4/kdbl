package de.ruegnerlukas.kdbl.dsl.expression

import de.ruegnerlukas.kdbl.utils.SqlDate
import de.ruegnerlukas.kdbl.utils.SqlTime


/**
 * A specific column of a [DerivedTable]
 */
class DerivedColumn<T>(val table: DerivedTable, val columnName: String) : Expr<T>


/**
 * A specific column of a specific table of a given type
 */
class Column<T>(val table: TableLike, val columnName: String, val type: DataType) : Expr<T> {

	internal val properties: MutableList<ColumnProperty> = mutableListOf()

	fun getProperties() = properties.toList()


	/**
	 * make this column a primary-key
	 */
	fun primaryKey() = this.also { properties.add(PrimaryKeyProperty()) }


	/**
	 * Make this colum auto-incrementing. This is only supported for integer-columns
	 */
	fun autoIncrement() = this.also { properties.add(AutoIncrementProperty()) }

	/**
	 * Don't allow "null"-values in this column
	 */
	fun notNull() = this.also { properties.add(NotNullProperty()) }


	/**
	 * Don't allow duplicate values in this column
	 */
	fun unique() = this.also { properties.add(UniqueProperty()) }


	/**
	 * make this column a foreign-key
	 */
	fun foreignKey(table: Table, onUpdate: RefAction = RefAction.NO_ACTION, onDelete: RefAction = RefAction.NO_ACTION) =
		this.also { properties.add(ForeignKeyConstraint(table, null, onUpdate, onDelete)) }


	/**
	 * make this column a foreign-key
	 */
	fun foreignKey(column: Column<*>, onUpdate: RefAction = RefAction.NO_ACTION, onDelete: RefAction = RefAction.NO_ACTION) =
		this.also { properties.add(ForeignKeyConstraint(column.table as Table, column, onUpdate, onDelete)) }

}

interface ColumnProperty

class PrimaryKeyProperty : ColumnProperty

interface DefaultValueProperty<T> : ColumnProperty
class DefaultBooleanValueProperty(val value: Boolean) : DefaultValueProperty<Boolean>
class DefaultShortValueProperty(val value: Short) : DefaultValueProperty<Short>
class DefaultIntValueProperty(val value: Int) : DefaultValueProperty<Int>
class DefaultLongValueProperty(val value: Long) : DefaultValueProperty<Long>
class DefaultFloatValueProperty(val value: Float) : DefaultValueProperty<Float>
class DefaultDoubleValueProperty(val value: Double) : DefaultValueProperty<Double>
class DefaultStringValueProperty(val value: String) : DefaultValueProperty<String>
class DefaultDateValueProperty(val value: SqlDate) : DefaultValueProperty<SqlDate>
class DefaultTimeValueProperty(val value: SqlTime) : DefaultValueProperty<SqlTime>

fun Column<Boolean>.defaultValue(value: Boolean) = this.also { properties.add(DefaultBooleanValueProperty(value)) }
fun Column<Short>.defaultValue(value: Short) = this.also { properties.add(DefaultShortValueProperty(value)) }
fun Column<Int>.defaultValue(value: Int) = this.also { properties.add(DefaultIntValueProperty(value)) }
fun Column<Long>.defaultValue(value: Long) = this.also { properties.add(DefaultLongValueProperty(value)) }
fun Column<Float>.defaultValue(value: Float) = this.also { properties.add(DefaultFloatValueProperty(value)) }
fun Column<Double>.defaultValue(value: Double) = this.also { properties.add(DefaultDoubleValueProperty(value)) }
fun Column<String>.defaultValue(value: String) = this.also { properties.add(DefaultStringValueProperty(value)) }
fun Column<SqlDate>.defaultValue(value: SqlDate) = this.also { properties.add(DefaultDateValueProperty(value)) }
fun Column<SqlTime>.defaultValue(value: SqlTime) = this.also { properties.add(DefaultTimeValueProperty(value)) }

class AutoIncrementProperty : ColumnProperty

class NotNullProperty : ColumnProperty

class UniqueProperty : ColumnProperty

class ForeignKeyConstraint(val table: Table, val column: Column<*>?, val onUpdate: RefAction, val onDelete: RefAction) : ColumnProperty

enum class RefAction {
	NO_ACTION,
	RESTRICT,
	SET_NULL,
	SET_DEFAULT,
	CASCADE,
}