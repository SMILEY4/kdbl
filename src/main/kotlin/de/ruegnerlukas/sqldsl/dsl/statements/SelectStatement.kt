package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Table

/**
 * A "SELECT"-clause
 */
class SelectStatement(val distinct: Boolean, val elements: List<SelectElement>)


/**
 * a single element of a "SELECT"-clause, e.g. a column, expression, aggregate-function, ...
 */
interface SelectElement


/**
 * An element indicating to select all columns. This must be the only element of a "FROM"-clause
 */
class SelectAllElement : SelectElement


/**
 * An element indicating to select all columns of the given table.
 */
class SelectAllFromTableElement(val table: Table) : SelectElement
