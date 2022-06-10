package de.ruegnerlukas.sqldsl.dsl.grammar.select


open class SelectStatement(val expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>, val distinct: Boolean = false)

// TODO: maybe not needed anymore
class SelectDistinctStatement(expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<*>>): de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectStatement(expressions, true)
