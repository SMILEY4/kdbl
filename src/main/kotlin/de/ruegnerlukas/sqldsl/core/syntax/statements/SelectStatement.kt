package de.ruegnerlukas.sqldsl.core.syntax.statements

import de.ruegnerlukas.sqldsl.core.syntax.select.SelectExpression

class SelectStatement(val distinct: Boolean, val expressions: List<SelectExpression>)