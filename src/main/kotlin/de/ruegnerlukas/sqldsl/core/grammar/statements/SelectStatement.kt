package de.ruegnerlukas.sqldsl.core.grammar.statements

import de.ruegnerlukas.sqldsl.core.grammar.select.SelectExpression

class SelectStatement(val distinct: Boolean, val expressions: List<SelectExpression>)