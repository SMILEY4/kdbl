package de.ruegnerlukas.sqldsl2.grammar.insert

interface InsertFieldsExpression

class InsertAllColumnsExpression: InsertFieldsExpression

class InsertColumnsExpression(val column: List<InsertColumn>): InsertFieldsExpression

interface InsertColumn

