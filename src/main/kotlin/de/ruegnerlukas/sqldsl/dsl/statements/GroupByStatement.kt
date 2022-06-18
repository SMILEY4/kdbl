package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Expr

/**
 * A "GROUP-BY"-clause
 */
class GroupByStatement(val elements: List<Expr<*>>)

