package de.ruegnerlukas.sqldsl2.grammar.aggregate

import de.ruegnerlukas.sqldsl2.grammar.expr.Expr
import de.ruegnerlukas.sqldsl2.grammar.select.AggregateSelectExpression

interface AggregateFunction : AggregateSelectExpression, Expr

class CountAllAggFunction : AggregateFunction

class CountAggFunction(val expression: Expr) : AggregateFunction

class MaxAggFunction(val expression: Expr) : AggregateFunction

class MinAggFunction(val expression: Expr) : AggregateFunction