package de.ruegnerlukas.sqldsl.dsl.grammar.insert

interface InsertFieldsStatement

class InsertAllColumnsStatement: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement

class InsertColumnsStatement(val columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression>):
	de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement

interface InsertColumnExpression

