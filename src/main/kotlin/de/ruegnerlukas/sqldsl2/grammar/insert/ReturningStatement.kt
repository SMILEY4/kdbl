package de.ruegnerlukas.sqldsl2.grammar.insert

interface ReturningStatement

class ReturnAllColumnsStatement: ReturningStatement

class ReturnColumnsStatement(val columns: List<ReturningColumn>): ReturningStatement

interface ReturningColumn