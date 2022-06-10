package de.ruegnerlukas.sqldsl.dsl.grammar.join

import de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr

interface JoinConstraint

class ConditionJoinConstraint(val condition: de.ruegnerlukas.sqldsl.dsl.grammar.expr.ConditionExpr):
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinConstraint

class UsingJoinConstraint(val columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.expr.ColumnExpr<*>>):
	de.ruegnerlukas.sqldsl.dsl.grammar.join.JoinConstraint
