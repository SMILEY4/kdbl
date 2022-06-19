package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Expr

/**
 * An "ORDER-BY"-clause
 */
class OrderByStatement(val elements: List<OrderByElement>)


/**
 * An element and direction to sort by
 */
class OrderByElement(val expr: Expr<*>, val dir: Dir)


/**
 * The possible sort-directions
 */
enum class Dir { ASC, DESC }

