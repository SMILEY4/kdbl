package de.ruegnerlukas.sqldsl.core.syntax.select

class AllColumnsSelectExpression : SelectExpression

fun all(): AllColumnsSelectExpression {
	return AllColumnsSelectExpression()
}
