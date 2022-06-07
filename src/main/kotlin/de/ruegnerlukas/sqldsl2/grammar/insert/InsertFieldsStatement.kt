package de.ruegnerlukas.sqldsl2.grammar.insert

interface InsertFieldsStatement

class InsertAllColumnsStatement: InsertFieldsStatement

class InsertColumnsStatement(val columns: List<InsertColumnExpression>): InsertFieldsStatement

interface InsertColumnExpression

