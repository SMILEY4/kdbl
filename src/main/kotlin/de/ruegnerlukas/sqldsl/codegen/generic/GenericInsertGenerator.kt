package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.InsertGenerator
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.NullLiteral
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnsStatement
import de.ruegnerlukas.sqldsl.dsl.grammar.column.OnConflict
import de.ruegnerlukas.sqldsl.dsl.schema.Table
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericInsertGenerator(private val genCtx: GeneratorContext) : InsertGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertStatement): Token {
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

	protected fun target(e: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
	}

	protected fun fields(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertAllColumnsStatement -> NoOpToken()
			is de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertColumnsStatement -> GroupToken(
				CsvListToken(
					e.columns.map {
						when (it) {
							is QualifiedColumn<*> -> genCtx.columnExpr().buildToken(it)
							else -> throwUnknownType(e)
						}
					}
				)
			)
			else -> throwUnknownType(e)
		}
	}


	protected fun content(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertContentStatement, fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement): Token {
		return ListToken()
			.add("VALUES")
			.add(
				when (e) {
					is de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertValuesExpression -> contentValues(e, fields)
					is de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertQueryExpression -> contentQuery(e)
					else -> throwUnknownType(e)
				}
			)
	}


	protected fun contentValues(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertValuesExpression, fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement): Token {
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


	protected fun contentQuery(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertQueryExpression): Token {
		return when (e) {
			is de.ruegnerlukas.sqldsl.dsl.grammar.query.QueryStatement<*> -> GroupToken(genCtx.query().buildToken(e))
			else -> throwUnknownType(e)
		}
	}


	protected fun returning(e: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement): Token {
		return ListToken()
			.add("RETURNING")
			.add(
				when (e) {
					is de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnAllColumnsStatement -> StringToken("*")
					is de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturnColumnsStatement -> CsvListToken(e.columns.map {
						when (it) {
							is QualifiedColumn<*> -> genCtx.columnExpr().buildToken(it)
							is AliasColumn<*> -> ListToken().add(genCtx.select().buildToken(it.getContent())).add("AS").add(it.getColumnName())
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