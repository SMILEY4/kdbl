package de.ruegnerlukas.sqldsl.dsl.grammar.insert

import de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue

interface InsertContentStatement

interface InsertQueryExpression: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertContentStatement

class InsertValuesExpression(val items: List<Map<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression, de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>>>):
	de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertContentStatement
