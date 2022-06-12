package de.ruegnerlukas.sqldsl.dsl.expr

import de.ruegnerlukas.sqldsl.utils.SqlDate
import de.ruegnerlukas.sqldsl.utils.SqlTime


class DerivedColumn<T>(val table: DerivedTable, val columnName: String) : Expr<T>

class Column<T>(val table: TableLike, val columnName: String, val type: DataType) : Expr<T> {

	internal val properties: MutableList<ColumnProperty> = mutableListOf()

	fun getProperties() = properties.toList()

	fun primaryKey(onConflict: OnConflict = OnConflict.ABORT) = this.also { properties.add(PrimaryKeyProperty(onConflict)) }

	fun autoIncrement() = this.also { properties.add(AutoIncrementProperty()) }

	fun notNull(onConflict: OnConflict = OnConflict.ABORT) = this.also { properties.add(NotNullProperty(onConflict)) }

	fun unique(onConflict: OnConflict = OnConflict.ABORT) = this.also { properties.add(UniqueProperty(onConflict)) }

	fun foreignKey(table: Table, onUpdate: OnUpdate = OnUpdate.NO_ACTION, onDelete: OnDelete = OnDelete.NO_ACTION) =
		this.also { properties.add(ForeignKeyConstraint(table, null, onUpdate, onDelete)) }

	fun foreignKey(column: Column<*>, onUpdate: OnUpdate = OnUpdate.NO_ACTION, onDelete: OnDelete = OnDelete.NO_ACTION) =
		this.also { properties.add(ForeignKeyConstraint(column.table as Table, column, onUpdate, onDelete)) }

}

interface ColumnProperty

class PrimaryKeyProperty(val onConflict: OnConflict) : ColumnProperty

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
class DefaultBlobValueProperty(val value: ByteArray) : DefaultValueProperty<ByteArray>

fun Column<Boolean>.defaultValue(value: Boolean) = this.also { properties.add(DefaultBooleanValueProperty(value)) }
fun Column<Short>.defaultValue(value: Short) = this.also { properties.add(DefaultShortValueProperty(value)) }
fun Column<Int>.defaultValue(value: Int) = this.also { properties.add(DefaultIntValueProperty(value)) }
fun Column<Long>.defaultValue(value: Long) = this.also { properties.add(DefaultLongValueProperty(value)) }
fun Column<Float>.defaultValue(value: Float) = this.also { properties.add(DefaultFloatValueProperty(value)) }
fun Column<Double>.defaultValue(value: Double) = this.also { properties.add(DefaultDoubleValueProperty(value)) }
fun Column<String>.defaultValue(value: String) = this.also { properties.add(DefaultStringValueProperty(value)) }
fun Column<SqlDate>.defaultValue(value: SqlDate) = this.also { properties.add(DefaultDateValueProperty(value)) }
fun Column<SqlTime>.defaultValue(value: SqlTime) = this.also { properties.add(DefaultTimeValueProperty(value)) }
fun Column<ByteArray>.defaultValue(value: ByteArray) = this.also { properties.add(DefaultBlobValueProperty(value)) }

class AutoIncrementProperty : ColumnProperty

class NotNullProperty(val onConflict: OnConflict) : ColumnProperty

class UniqueProperty(val onConflict: OnConflict) : ColumnProperty

class ForeignKeyConstraint(val table: Table, val column: Column<*>?, val onUpdate: OnUpdate, val onDelete: OnDelete) : ColumnProperty

enum class OnConflict {
	ROLLBACK,
	ABORT,
	FAIL,
	IGNORE,
	REPLACE
}

enum class OnDelete {
	NO_ACTION,
	RESTRICT,
	SET_NULL,
	SET_DEFAULT,
	CASCADE,
}

enum class OnUpdate {
	NO_ACTION,
	RESTRICT,
	SET_NULL,
	SET_DEFAULT,
	CASCADE,
}