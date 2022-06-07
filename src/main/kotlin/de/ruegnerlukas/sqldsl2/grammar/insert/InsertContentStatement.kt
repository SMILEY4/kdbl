package de.ruegnerlukas.sqldsl2.grammar.insert

import de.ruegnerlukas.sqldsl2.grammar.expr.LiteralValue

interface InsertContentStatement

interface InsertQueryExpression: InsertContentStatement

class InsertValuesExpression(val items: List<Map<InsertColumnExpression,LiteralValue<*>>>): InsertContentStatement
