package de.ruegnerlukas.sqldsl.dsl.expr

interface Returning

class ReturnAllColumns : Returning

class ReturnColumns(val columns: List<Expr<*>>) : Returning