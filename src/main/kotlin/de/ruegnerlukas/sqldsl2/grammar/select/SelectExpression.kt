package de.ruegnerlukas.sqldsl2.grammar.select

import de.ruegnerlukas.sqldsl2.grammar.TableLike

interface SelectExpression

interface ExprSelectExpression : SelectExpression

class AllSelectExpression : SelectExpression

class QualifiedAllSelectExpression(val qualifier: TableLike) : SelectExpression

interface AggregateSelectExpression : SelectExpression

class AliasSelectExpression(val expr: SelectExpression, val alias: String) : SelectExpression


