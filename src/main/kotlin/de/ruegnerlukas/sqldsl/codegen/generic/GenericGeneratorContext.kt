package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext

class GenericGeneratorContext : GeneratorContext {

	private val queryGenerator = GenericQueryGenerator(this)
	private val insertGenerator = GenericInsertGenerator(this)
	private val updateGenerator = GenericUpdateGenerator(this)
	private val deleteGenerator = GenericDeleteGenerator(this)
	private val createGenerator = GenericCreateGenerator(this)
	private val selectGenerator = GenericSelectExpressionGenerator(this)
	private val fromGenerator = GenericFromExpressionGenerator(this)
	private val whereGenerator = GenericWhereExpressionGenerator(this)
	private val groupByGenerator = GenericGroupByExpressionGenerator(this)
	private val havingGenerator = GenericHavingExpressionGenerator(this)
	private val orderByGenerator = GenericOrderByExpressionGenerator(this)
	private val aggFuncGenerator = GenericAggregateFunctionGenerator(this)
	private val exprGenerator = GenericExpressionGenerator(this)
	private val columnExprGenerator = GenericColumnExprGenerator()
	private val conditionGenerator = GenericConditionGenerator(this)
	private val literalGenerator = GenericLiteralValueGenerator(this)
	private val operationGenerator = GenericOperationExprGenerator(this)
	private val joinClauseGenerator = GenericJoinClauseGenerator(this)

	override fun query() = queryGenerator

	override fun insert() = insertGenerator

	override fun update() = updateGenerator

	override fun delete() = deleteGenerator

	override fun create() = createGenerator

	override fun select() = selectGenerator

	override fun from() = fromGenerator

	override fun where() = whereGenerator

	override fun groupBy() = groupByGenerator

	override fun having() = havingGenerator

	override fun orderBy() = orderByGenerator

	override fun aggFunc() = aggFuncGenerator

	override fun expr() = exprGenerator

	override fun columnExpr() = columnExprGenerator

	override fun condition() = conditionGenerator

	override fun literal() = literalGenerator

	override fun operation() = operationGenerator

	override fun joinClause() = joinClauseGenerator

}