package de.ruegnerlukas.sqldsl2.grammar.from

import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement


interface FromExpression

interface TableFromExpression : FromExpression

class QueryFromExpression(val query: QueryStatement, val alias: String) : FromExpression

interface JoinFromExpression: FromExpression

