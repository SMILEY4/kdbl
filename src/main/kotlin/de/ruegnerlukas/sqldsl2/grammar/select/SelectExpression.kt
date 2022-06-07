package de.ruegnerlukas.sqldsl2.grammar.select

import de.ruegnerlukas.sqldsl2.grammar.table.TableBase
import de.ruegnerlukas.sqldsl2.schema.AnyValueType
import de.ruegnerlukas.sqldsl2.schema.NoValueType


interface SelectExpression<T: AnyValueType>

interface ExprSelectExpression<T: AnyValueType> : SelectExpression<T>

class AllSelectExpression : SelectExpression<NoValueType>

class QualifiedAllSelectExpression(val qualifier: TableBase) : SelectExpression<NoValueType>

interface AliasSelectExpression<T: AnyValueType> : SelectExpression<T>


