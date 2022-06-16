package de.ruegnerlukas.sqldsl.dsl.expression

interface Returning

class ReturnAllColumns : Returning

class ReturnColumns(val columns: List<Expr<*>>) : Returning