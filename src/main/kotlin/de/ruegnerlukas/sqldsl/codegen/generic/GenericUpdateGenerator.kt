package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.codegen.UpdateGenerator
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl.dsl.grammar.column.OnConflict
import de.ruegnerlukas.sqldsl.dsl.schema.Table
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericUpdateGenerator(private val genCtx: GeneratorContext) : UpdateGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateStatement): Token {
		return ListToken()
			.add(update(e.onConflict))
			.add(target(e.target))
			.add(set(e.set))
			.addIf(e.from != null) { from(e.from!!) }
			.addIf(e.where != null) { where(e.where!!) }
			.addIf(e.returning != null) { returning(e.returning!!) }
	}


	protected fun update(onConflict: OnConflict): Token {
		return ListToken()
			.add("UPDATE")
			.addIf(onConflict != OnConflict.ABORT, mapOnConflict(onConflict))
	}

	protected fun target(e: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
	}

	protected fun set(e: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateSetStatement): Token {
		return ListToken()
			.add("SET")
			.add(CsvListToken(
				e.expressions.map {
					ListToken()
						.add(
							when (it.column) {
								is QualifiedColumn<*> -> genCtx.columnExpr().buildToken(it.column)
								else -> throwUnknownType(it)
							}
						)
						.add("=")
						.add(genCtx.expr().buildToken(it.value))
				}
			))
	}


	protected fun from(e: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(CsvListToken(e.expressions.map { genCtx.from().buildToken(it) }))
	}


	protected fun where(e: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(genCtx.where().buildToken(e.expression))
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