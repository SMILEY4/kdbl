package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expression.Expr

class WhereStatement(val condition: Expr<Boolean>)