package de.ruegnerlukas.sqldsl2.grammar

import de.ruegnerlukas.sqldsl2.grammar.from.TableFromExpression

interface TableLike: TableFromExpression

class Table(val name: String): TableLike
