package de.ruegnerlukas.sqldsl.core.actions.query

interface SelectColumn


class AliasSelectColumn(val selectColumn: SelectColumn, val alias: String) : SelectColumn

fun SelectColumn.alias(alias: String) = AliasSelectColumn(this, alias)


class AllSelectColumn : SelectColumn

fun all() = AllSelectColumn()


class AllOfTableSelectColumn(val table: TableRef) : SelectColumn

fun all(table: TableRef) = AllOfTableSelectColumn(table)


interface FunctionSelectColumn : SelectColumn

class CountAllSelectColumn: FunctionSelectColumn

fun count() = CountAllSelectColumn()

class CountSelectColumn(val column: ColumnRef<*,*>): FunctionSelectColumn

fun count(column: ColumnRef<*,*>) = CountSelectColumn(column)



