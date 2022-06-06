package de.ruegnerlukas.sqldsl2.grammar.update

import de.ruegnerlukas.sqldsl2.grammar.expr.Expr

class UpdateSetStatement(val expressions: List<UpdateExpression>)

class UpdateExpression(val column: UpdateColumn, val value: Expr)

interface UpdateColumn
