package de.ruegnerlukas.kdbl.dsl.statements

import de.ruegnerlukas.kdbl.dsl.expression.Expr

/**
 * A "HAVING"-clause
 */
class HavingStatement(val condition: Expr<Boolean>)