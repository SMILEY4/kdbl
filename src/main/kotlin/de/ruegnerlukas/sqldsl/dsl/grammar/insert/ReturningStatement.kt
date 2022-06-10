package de.ruegnerlukas.sqldsl.dsl.grammar.insert

interface ReturningStatement

class ReturnAllColumnsStatement: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement

class ReturnColumnsStatement(val columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn>):
	de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement

interface ReturningColumn