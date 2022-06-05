package de.ruegnerlukas.sqldsl2.grammar.select

import de.ruegnerlukas.sqldsl2.grammar.table.TableBase


interface SelectExpression

interface ExprSelectExpression : SelectExpression

class AllSelectExpression : SelectExpression

class QualifiedAllSelectExpression(val qualifier: TableBase) : SelectExpression

interface AliasSelectExpression : SelectExpression


