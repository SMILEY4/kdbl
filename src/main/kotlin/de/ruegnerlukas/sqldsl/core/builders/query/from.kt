package de.ruegnerlukas.sqldsl.core.builders.query

import de.ruegnerlukas.sqldsl.core.syntax.from.FromExpression
import de.ruegnerlukas.sqldsl.core.syntax.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.syntax.statements.SelectStatement


class PostSelectQueryBuilder(private val selectStatement: SelectStatement) {

	fun from(vararg expressions: FromExpression): PostFromQueryBuilder {
		return PostFromQueryBuilder(selectStatement, FromStatement(expressions.toList()))
	}

}
