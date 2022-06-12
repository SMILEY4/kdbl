package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.Expr

class HavingStatement(val condition: Expr<Boolean>)