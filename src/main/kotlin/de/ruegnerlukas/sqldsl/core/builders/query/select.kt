package de.ruegnerlukas.sqldsl.core.builders.query

import de.ruegnerlukas.sqldsl.core.syntax.refs.table.TableRef
import de.ruegnerlukas.sqldsl.core.syntax.select.AllColumnsSelectExpression
import de.ruegnerlukas.sqldsl.core.syntax.select.SelectExpression
import de.ruegnerlukas.sqldsl.core.syntax.statements.SelectStatement



fun query() = QueryBuilder()


class QueryBuilder {

	fun select(vararg expressions: SelectExpression): PostSelectQueryBuilder {
		return PostSelectQueryBuilder(SelectStatement(false, expressions.toList()))
	}

	fun selectDistinct(vararg expressions: SelectExpression): PostSelectQueryBuilder {
		return PostSelectQueryBuilder(SelectStatement(true, expressions.toList()))
	}

}


fun all(): AllColumnsSelectExpression {
	return AllColumnsSelectExpression()
}

fun all(table: TableRef): AllColumnsSelectExpression {
	return AllColumnsSelectExpression()
}


