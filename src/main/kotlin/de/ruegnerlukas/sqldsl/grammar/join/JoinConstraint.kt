package de.ruegnerlukas.sqldsl.grammar.join

import de.ruegnerlukas.sqldsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.grammar.expr.ConditionExpr

interface JoinConstraint

class ConditionJoinConstraint(val condition: ConditionExpr): JoinConstraint

class UsingJoinConstraint(val columns: List<ColumnExpr<*>>): JoinConstraint
