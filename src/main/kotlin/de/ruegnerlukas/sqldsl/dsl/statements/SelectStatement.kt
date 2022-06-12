package de.ruegnerlukas.sqldsl.dsl.statements

import de.ruegnerlukas.sqldsl.dsl.expr.Table

class SelectStatement(val distinct: Boolean, val elements: List<SelectElement>)

interface SelectElement

class SelectAllElement : SelectElement

class SelectAllFromTableElement(val table: Table) : SelectElement