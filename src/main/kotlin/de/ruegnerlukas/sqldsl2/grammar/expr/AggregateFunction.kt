package de.ruegnerlukas.sqldsl2.grammar.expr


interface AggregateFunction : Expr

class CountAllAggFunction : AggregateFunction

class CountAggFunction(val expression: Expr) : AggregateFunction

class MaxAggFunction(val expression: Expr) : AggregateFunction

class MinAggFunction(val expression: Expr) : AggregateFunction