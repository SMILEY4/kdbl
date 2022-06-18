package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Expr

/**
 * A "HAVING"-clause
 */
class HavingStatement(val condition: Expr<Boolean>)