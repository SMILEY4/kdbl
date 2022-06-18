package de.ruegnerlukas.sqldsl.dsl.statements

/**
 * A "FROM"-clause
 */
class FromStatement(val elements: List<FromElement>)


/**
 * a single element of a "FROM"-clause, i.e. a table or a join-clause
 */
interface FromElement
