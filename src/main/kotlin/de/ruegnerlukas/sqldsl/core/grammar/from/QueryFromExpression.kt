package de.ruegnerlukas.sqldsl.core.grammar.from

import de.ruegnerlukas.sqldsl.core.grammar.statements.QueryStatement

class QueryFromExpression(val query: QueryStatement, val alias: String) : FromExpression

fun QueryStatement.alias(alias: String) = QueryFromExpression(this, alias)
