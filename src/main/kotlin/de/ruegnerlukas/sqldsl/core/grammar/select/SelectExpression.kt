package de.ruegnerlukas.sqldsl.core.grammar.select

interface SelectExpression

class AllColumnsSelectExpression : SelectExpression

class AliasSelectExpression(val source: SelectExpression, val alias: String) : SelectExpression

