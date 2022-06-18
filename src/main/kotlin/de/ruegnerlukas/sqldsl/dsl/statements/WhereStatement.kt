package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Expr

/**
 * A "WHERE"-clause
 */
class WhereStatement(val condition: Expr<Boolean>)