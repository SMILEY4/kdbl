package de.ruegnerlukas.sqldsl2.grammar.select

import de.ruegnerlukas.sqldsl2.grammar.table.TableBase


interface SelectExpression

interface ExprSelectExpression : SelectExpression

class AllSelectExpression : SelectExpression

class QualifiedAllSelectExpression(val qualifier: TableBase) : SelectExpression

class AliasSelectExpression(val expr: SelectExpression, val alias: String) : SelectExpression


