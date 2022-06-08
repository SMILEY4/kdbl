package de.ruegnerlukas.sqldsl.grammar.update

import de.ruegnerlukas.sqldsl.grammar.expr.Expr

class UpdateSetStatement(val expressions: List<UpdateExpression>)

class UpdateExpression(val column: UpdateColumn, val value: Expr<*>)

interface UpdateColumn
