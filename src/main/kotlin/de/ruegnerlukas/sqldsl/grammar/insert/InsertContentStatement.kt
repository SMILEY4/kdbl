package de.ruegnerlukas.sqldsl.grammar.insert

import de.ruegnerlukas.sqldsl.grammar.expr.LiteralValue

interface InsertContentStatement

interface InsertQueryExpression: InsertContentStatement

class InsertValuesExpression(val items: List<Map<InsertColumnExpression,LiteralValue<*>>>): InsertContentStatement
