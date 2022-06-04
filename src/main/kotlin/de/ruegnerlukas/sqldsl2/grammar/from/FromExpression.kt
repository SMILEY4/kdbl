package de.ruegnerlukas.sqldsl2.grammar.from

import de.ruegnerlukas.sqldsl2.grammar.TableLike
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement


interface FromExpression

interface TableFromExpression : TableLike

interface TableAliasFromExpression : FromExpression

class QueryFromExpression(val query: QueryStatement, val alias: String) : FromExpression

interface JoinFromExpression: FromExpression

