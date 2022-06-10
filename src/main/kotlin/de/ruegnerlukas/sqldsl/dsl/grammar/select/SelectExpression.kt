package de.ruegnerlukas.sqldsl.dsl.grammar.select

import de.ruegnerlukas.sqldsl.dsl.grammar.column.AnyValueType
import de.ruegnerlukas.sqldsl.dsl.grammar.column.NoValueType


interface SelectExpression<T: AnyValueType>

interface ExprSelectExpression<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>

class AllSelectExpression : de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<NoValueType>

class QualifiedAllSelectExpression(val qualifier: de.ruegnerlukas.sqldsl.dsl.grammar.table.TableBase) :
	de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<NoValueType>

interface AliasSelectExpression<T: AnyValueType> : de.ruegnerlukas.sqldsl.dsl.grammar.select.SelectExpression<T>


