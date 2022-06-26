package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Expr

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