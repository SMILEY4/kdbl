package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Expr

/**
 * A "GROUP-BY"-clause
 */
class GroupByStatement(val elements: List<Expr<*>>)

