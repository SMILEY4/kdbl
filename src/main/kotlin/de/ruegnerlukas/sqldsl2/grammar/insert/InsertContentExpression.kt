package de.ruegnerlukas.sqldsl2.grammar.insert

interface InsertContentExpression

interface InsertQueryExpression: InsertContentExpression

class InsertValuesExpression(val items: List<Map<InsertColumn,Any>>): InsertContentExpression
