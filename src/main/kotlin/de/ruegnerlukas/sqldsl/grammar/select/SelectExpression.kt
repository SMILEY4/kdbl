package de.ruegnerlukas.sqldsl.grammar.select

import de.ruegnerlukas.sqldsl.grammar.table.TableBase
import de.ruegnerlukas.sqldsl.schema.AnyValueType
import de.ruegnerlukas.sqldsl.schema.NoValueType


interface SelectExpression<T: AnyValueType>

interface ExprSelectExpression<T: AnyValueType> : SelectExpression<T>

class AllSelectExpression : SelectExpression<NoValueType>

class QualifiedAllSelectExpression(val qualifier: TableBase) : SelectExpression<NoValueType>

interface AliasSelectExpression<T: AnyValueType> : SelectExpression<T>


