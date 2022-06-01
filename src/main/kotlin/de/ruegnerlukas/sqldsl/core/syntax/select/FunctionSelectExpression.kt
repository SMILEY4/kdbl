package de.ruegnerlukas.sqldsl.core.syntax.select

interface FunctionSelectExpression: SelectExpression

class CountAllSelectExpression(val alias: String): FunctionSelectExpression