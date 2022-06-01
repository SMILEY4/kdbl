package de.ruegnerlukas.sqldsl.core.builders.query

import de.ruegnerlukas.sqldsl.core.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.core.grammar.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.SelectStatement


class PostSelectQueryBuilder(private val selectStatement: SelectStatement) {

	fun from(vararg expressions: FromExpression): PostFromQueryBuilder {
		return PostFromQueryBuilder(selectStatement, FromStatement(expressions.toList()))
	}

}
