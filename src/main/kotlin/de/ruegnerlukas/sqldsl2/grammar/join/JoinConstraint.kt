package de.ruegnerlukas.sqldsl2.grammar.join

import de.ruegnerlukas.sqldsl2.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl2.grammar.expr.ConditionExpr

interface JoinConstraint

class ConditionJoinConstraint(val condition: ConditionExpr): JoinConstraint

class UsingJoinConstraint(val columns: List<ColumnExpr>): JoinConstraint
