package de.ruegnerlukas.sqldsl2.grammar.expr

import de.ruegnerlukas.sqldsl2.grammar.TableLike

interface ColumnExpr : Expr

class Column(val columnName: String) : ColumnExpr

class QualifiedColumn(val qualifier: TableLike, val column: Column) : ColumnExpr