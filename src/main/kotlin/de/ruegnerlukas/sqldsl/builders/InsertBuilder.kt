package de.ruegnerlukas.sqldsl.builders

import de.ruegnerlukas.sqldsl.grammar.expr.LiteralValue
import de.ruegnerlukas.sqldsl.grammar.insert.InsertAllColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertColumnExpression
import de.ruegnerlukas.sqldsl.grammar.insert.InsertColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertContentStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertFieldsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertQueryExpression
import de.ruegnerlukas.sqldsl.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl.grammar.insert.InsertValuesExpression
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturningColumn
import de.ruegnerlukas.sqldsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.schema.OnConflict

interface InsertBuilderEndStep {
	fun build(): InsertStatement
}

class InsertBuilder {

	fun insertInto(target: CommonTarget) = PostTargetInsertBuilder(target)

}


class PostTargetInsertBuilder(private val target: CommonTarget) {

	fun or(onConflict: OnConflict) = PostConflictBuilder(target, onConflict)
	fun orRollback() = or(OnConflict.ROLLBACK)
	fun orAbort() = or(OnConflict.ABORT)
	fun orFail() = or(OnConflict.FAIL)
	fun orIgnore() = or(OnConflict.IGNORE)
	fun orReplace() = or(OnConflict.REPLACE)

	fun fields(columns: List<InsertColumnExpression>) = PostFieldsBuilder(target, OnConflict.ABORT, InsertColumnsStatement(columns))

	fun fields(vararg columns: InsertColumnExpression) = PostFieldsBuilder(target, OnConflict.ABORT, InsertColumnsStatement(columns.toList()))

	fun allFields() = PostFieldsBuilder(target, OnConflict.ABORT, InsertAllColumnsStatement())

}

class PostConflictBuilder(private val target: CommonTarget, private val onConflict: OnConflict) {

	fun fields(vararg columns: InsertColumnExpression) = PostFieldsBuilder(target, onConflict, InsertColumnsStatement(columns.toList()))

	fun allFields() = PostFieldsBuilder(target, onConflict, InsertAllColumnsStatement())

}

class PostFieldsBuilder(private val target: CommonTarget, private val onConflict: OnConflict, private val fields: InsertFieldsStatement) {

	fun fromQuery(query: InsertQueryExpression) = PostQueryContentBuilder(target, onConflict, fields, query)

	fun fromQuery(query: QueryBuilderEndStep<*>) = PostQueryContentBuilder(target, onConflict, fields, query.build())

	fun item(item: Map<InsertColumnExpression, LiteralValue<*>>) = PostItemContentBuilder(target, onConflict, fields, item)

}

class PostQueryContentBuilder(
	private val target: CommonTarget,
	private val onConflict: OnConflict,
	private val fields: InsertFieldsStatement,
	private val content: InsertContentStatement
) : InsertBuilderEndStep {

	fun returningAll() = PostReturningBuilder(target, onConflict, fields, content, ReturnAllColumnsStatement())

	fun returning(columns: List<ReturningColumn>) = PostReturningBuilder(target, onConflict, fields, content, ReturnColumnsStatement(columns))

	fun returning(vararg columns: ReturningColumn) = PostReturningBuilder(target, onConflict, fields, content, ReturnColumnsStatement(columns.toList()))

	override fun build() = InsertStatement(onConflict, target, fields, content, null)

}

class PostItemContentBuilder(
	private val target: CommonTarget,
	private val onConflict: OnConflict,
	private val fields: InsertFieldsStatement,
	item: Map<InsertColumnExpression, LiteralValue<*>>
) : InsertBuilderEndStep {

	private val items = mutableListOf(item)

	fun item(item: Map<InsertColumnExpression, LiteralValue<*>>): PostItemContentBuilder {
		items.add(item)
		return this
	}

	fun returningAll() = PostReturningBuilder(target, onConflict, fields, InsertValuesExpression(items), ReturnAllColumnsStatement())

	fun returning(columns: List<ReturningColumn>) = PostReturningBuilder(target, onConflict, fields, InsertValuesExpression(items), ReturnColumnsStatement(columns))

	fun returning(vararg columns: ReturningColumn) = PostReturningBuilder(target, onConflict, fields, InsertValuesExpression(items), ReturnColumnsStatement(columns.toList()))

	override fun build() = InsertStatement(onConflict, target, fields, InsertValuesExpression(items), null)

}


class PostReturningBuilder(
	private val target: CommonTarget,
	private val onConflict: OnConflict,
	private val fields: InsertFieldsStatement,
	private val content: InsertContentStatement,
	private val returning: ReturningStatement
) : InsertBuilderEndStep {

	override fun build() = InsertStatement(onConflict, target, fields, content, returning)

}