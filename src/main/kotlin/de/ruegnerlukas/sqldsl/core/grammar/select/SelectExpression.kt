package de.ruegnerlukas.sqldsl.core.grammar.select

import de.ruegnerlukas.sqldsl.core.grammar.refs.ColumnRef
import de.ruegnerlukas.sqldsl.core.schema.Table

interface SelectExpression

interface WildcardSelectExpression : SelectExpression

class AllColumnsSelectExpression : WildcardSelectExpression

class AllColumnsOfSelectExpression(val table: Table) : WildcardSelectExpression

class ColumnSelectExpression(val column: ColumnRef, val alias: String) : SelectExpression
