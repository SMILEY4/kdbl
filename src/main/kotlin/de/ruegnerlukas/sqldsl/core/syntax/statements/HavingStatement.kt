package de.ruegnerlukas.sqldsl.core.syntax.statements

import de.ruegnerlukas.sqldsl.core.syntax.expression.Expression


class HavingStatement(val expression: Expression<*>)