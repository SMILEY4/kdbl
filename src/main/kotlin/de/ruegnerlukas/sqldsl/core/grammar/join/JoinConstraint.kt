package de.ruegnerlukas.sqldsl.core.grammar.join

import de.ruegnerlukas.sqldsl.core.grammar.expression.Expression
import de.ruegnerlukas.sqldsl.core.grammar.refs.ColumnRef

interface JoinConstraint

class ConditionJoinConstraint(val expression: Expression) : JoinConstraint

class UsingJoinConstraint(val columns: List<ColumnRef<*, *>>) : JoinConstraint