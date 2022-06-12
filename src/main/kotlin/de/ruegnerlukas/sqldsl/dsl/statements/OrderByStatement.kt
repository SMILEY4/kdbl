package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.Expr

class OrderByStatement(val elements: List<OrderByElement>)

class OrderByElement(val expr: Expr<*>, val dir: Dir)

enum class Dir { ASC, DESC }

