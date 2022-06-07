package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.InsertGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.NullLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertAllColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertContentStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertFieldsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertQueryExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.InsertValuesExpression
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.schema.OnConflict
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.tokens.CsvListToken
import de.ruegnerlukas.sqldsl2.tokens.GroupToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.NoOpToken
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericInsertGenerator(private val genCtx: GeneratorContext) : InsertGenerator, GenericGeneratorBase<InsertStatement>() {

	override fun buildToken(e: InsertStatement): Token {
		return ListToken()
			.add(insert(e.onConflict))
			.add(target(e.target))
			.add(fields(e.fields))
			.add(content(e.content, e.fields))
			.addIf(e.returning != null) { returning(e.returning!!) }
	}


	protected fun insert(onConflict: OnConflict): Token {
		return ListToken()
			.add("INSERT")
			.addIf(onConflict != OnConflict.ABORT, mapOnConflict(onConflict))
			.add("INTO")
	}

	protected fun target(e: CommonTarget): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
	}

	protected fun fields(e: InsertFieldsStatement): Token {
		return when (e) {
			is InsertAllColumnsStatement -> NoOpToken()
			is InsertColumnsStatement -> GroupToken(
				CsvListToken(
					e.columns.map {
						when (it) {
							is QualifiedColumn -> genCtx.columnExpr().buildToken(it)
							else -> throwUnknownType(e)
						}
					}
				)
			)
			else -> throwUnknownType(e)
		}
	}


	protected fun content(e: InsertContentStatement, fields: InsertFieldsStatement): Token {
		return ListToken()
			.add("VALUES")
			.add(
				when (e) {
					is InsertValuesExpression -> contentValues(e, fields)
					is InsertQueryExpression -> contentQuery(e)
					else -> throwUnknownType(e)
				}
			)
	}


	protected fun contentValues(e: InsertValuesExpression, fields: InsertFieldsStatement): Token {
		return CsvListToken(
			e.items.map { item ->
				if (fields is InsertColumnsStatement) {
					GroupToken(
						CsvListToken(
							fields.columns.map {
								genCtx.literal().buildToken(if (item.containsKey(it)) item[it]!! else NullLiteral())
							}
						)
					)
				} else {
					throw IllegalStateException("Specifying columns is required when inserting items")
				}
			}
		)
	}


	protected fun contentQuery(e: InsertQueryExpression): Token {
		return when (e) {
			is QueryStatement -> GroupToken(genCtx.query().buildToken(e))
			else -> throwUnknownType(e)
		}
	}


	protected fun returning(e: ReturningStatement): Token {
		return ListToken()
			.add("RETURNING")
			.add(
				when (e) {
					is ReturnAllColumnsStatement -> StringToken("*")
					is ReturnColumnsStatement -> CsvListToken(e.columns.map {
						when (it) {
							is QualifiedColumn -> genCtx.columnExpr().buildToken(it)
							is AliasColumn -> ListToken().add(genCtx.select().buildToken(it.getContent())).add("AS").add(it.getColumnName())
							else -> throwUnknownType(it)
						}
					})
					else -> throwUnknownType(e)
				}
			)
	}

	protected fun mapOnConflict(onConflict: OnConflict): String {
		return when (onConflict) {
			OnConflict.ROLLBACK -> "OR ROLLBACK"
			OnConflict.ABORT -> "OR ABORT"
			OnConflict.FAIL -> "OR FAIL"
			OnConflict.IGNORE -> "OR IGNORE"
			OnConflict.REPLACE -> "OR REPLACE"
		}
	}


}