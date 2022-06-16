package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Expr

class HavingStatement(val condition: Expr<Boolean>)