package de.ruegnerlukas.sqldsl2.grammar.select


open class SelectStatement(val expressions: List<SelectExpression>, val distinct: Boolean = false)

class SelectDistinctStatement(expressions: List<SelectExpression>): SelectStatement(expressions, true)
