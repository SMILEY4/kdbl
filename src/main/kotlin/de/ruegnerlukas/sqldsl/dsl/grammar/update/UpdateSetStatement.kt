package de.ruegnerlukas.sqldsl.dsl.grammar.update

import de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr

class UpdateSetStatement(val expressions: List<de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateExpression>)

class UpdateExpression(val column: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateColumn, val value: de.ruegnerlukas.sqldsl.dsl.grammar.expr.Expr<*>)

interface UpdateColumn
