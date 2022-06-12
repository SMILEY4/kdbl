package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.Expr

class WhereStatement(val condition: Expr<Boolean>)