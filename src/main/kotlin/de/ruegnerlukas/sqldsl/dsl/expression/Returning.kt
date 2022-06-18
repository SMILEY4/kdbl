package de.ruegnerlukas.sqldsl.dsl.expression

/**
 * A "RETURNING"-clause
 */
interface Returning


/**
 * Return all columns
 */
class ReturnAllColumns : Returning


/**
 * Return only the specified columns/expressions
 */
class ReturnColumns(val columns: List<Expr<*>>) : Returning