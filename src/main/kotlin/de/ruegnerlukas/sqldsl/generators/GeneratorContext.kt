package de.ruegnerlukas.sqldsl.generators

interface GeneratorContext {
	fun query(): QueryGenerator
	fun insert(): InsertGenerator
	fun update(): UpdateGenerator
	fun delete(): DeleteGenerator
	fun create(): CreateGenerator

	fun select(): SelectExpressionGenerator
	fun from(): FromExpressionGenerator
	fun where(): WhereExpressionGenerator
	fun groupBy(): GroupByExpressionGenerator
	fun having(): HavingExpressionGenerator
	fun orderBy(): OrderByExpressionGenerator
	fun aggFunc(): AggregateFunctionGenerator
	fun expr(): ExpressionGenerator
	fun columnExpr(): ColumnExprGenerator
	fun condition(): ConditionGenerator
	fun literal(): LiteralGenerator
	fun operation(): OperationExprGenerator
	fun joinClause(): JoinClauseGenerator
}