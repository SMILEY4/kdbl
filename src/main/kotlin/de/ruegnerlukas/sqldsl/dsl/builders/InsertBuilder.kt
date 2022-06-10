package de.ruegnerlukas.sqldsl.dsl.builders

import de.ruegnerlukas.sqldsl.dsl.grammar.column.OnConflict

interface InsertBuilderEndStep {
	fun build(): de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement
}

class InsertBuilder {

	fun insertInto(target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget) = PostTargetInsertBuilder(target)

}


class PostTargetInsertBuilder(private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget) {

	fun or(onConflict: OnConflict) = PostConflictBuilder(target, onConflict)
	fun orRollback() = or(OnConflict.ROLLBACK)
	fun orAbort() = or(OnConflict.ABORT)
	fun orFail() = or(OnConflict.FAIL)
	fun orIgnore() = or(OnConflict.IGNORE)
	fun orReplace() = or(OnConflict.REPLACE)

	fun fields(columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression>) = PostFieldsBuilder(target, OnConflict.ABORT,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnsStatement(columns)
	)

	fun fields(vararg columns: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression) = PostFieldsBuilder(target, OnConflict.ABORT,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnsStatement(columns.toList())
	)

	fun allFields() = PostFieldsBuilder(target, OnConflict.ABORT, de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertAllColumnsStatement())

}

class PostConflictBuilder(private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget, private val onConflict: OnConflict) {

	fun fields(vararg columns: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression) = PostFieldsBuilder(target, onConflict,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnsStatement(columns.toList())
	)

	fun allFields() = PostFieldsBuilder(target, onConflict, de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertAllColumnsStatement())

}

class PostFieldsBuilder(private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget, private val onConflict: OnConflict, private val fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement) {

	fun fromQuery(query: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertQueryExpression) = PostQueryContentBuilder(target, onConflict, fields, query)

	fun fromQuery(query: QueryBuilderEndStep<*>) = PostQueryContentBuilder(target, onConflict, fields, query.build())

	fun item(item: Map<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression, de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>>) = PostItemContentBuilder(target, onConflict, fields, item)

}

class PostQueryContentBuilder(
	private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget,
	private val onConflict: OnConflict,
	private val fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement,
	private val content: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertContentStatement
) : InsertBuilderEndStep {

	fun returningAll() = PostReturningBuilder(target, onConflict, fields, content,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnAllColumnsStatement()
	)

	fun returning(columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn>) = PostReturningBuilder(target, onConflict, fields, content,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(columns)
	)

	fun returning(vararg columns: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn) = PostReturningBuilder(target, onConflict, fields, content,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(columns.toList())
	)

	override fun build() = de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement(onConflict, target, fields, content, null)

}

class PostItemContentBuilder(
	private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget,
	private val onConflict: OnConflict,
	private val fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement,
	item: Map<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression, de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>>
) : InsertBuilderEndStep {

	private val items = mutableListOf(item)

	fun item(item: Map<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnExpression, de.ruegnerlukas.sqldsl.dsl.grammar.expr.LiteralValue<*>>): PostItemContentBuilder {
		items.add(item)
		return this
	}

	fun returningAll() = PostReturningBuilder(target, onConflict, fields,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertValuesExpression(items),
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnAllColumnsStatement()
	)

	fun returning(columns: List<de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn>) = PostReturningBuilder(target, onConflict, fields,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertValuesExpression(items),
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(columns)
	)

	fun returning(vararg columns: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningColumn) = PostReturningBuilder(target, onConflict, fields,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertValuesExpression(items),
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement(columns.toList())
	)

	override fun build() = de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement(
		onConflict,
		target,
		fields,
		de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertValuesExpression(items),
		null
	)

}


class PostReturningBuilder(
	private val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget,
	private val onConflict: OnConflict,
	private val fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement,
	private val content: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertContentStatement,
	private val returning: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement
) : InsertBuilderEndStep {

	override fun build() = de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement(onConflict, target, fields, content, returning)

}