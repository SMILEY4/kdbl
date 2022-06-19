package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Expr

/**
 * A "WHERE"-clause
 */
class WhereStatement(val condition: Expr<Boolean>)