package de.ruegnerlukas.sqldsl.core.syntax.from

import de.ruegnerlukas.sqldsl.core.syntax.statements.QueryStatement

class QueryFromExpression(val query: QueryStatement, val alias: String) : FromExpression

fun QueryStatement.alias(alias: String) = QueryFromExpression(this, alias)
