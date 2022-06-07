package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.generators.UpdateGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateSetStatement
import de.ruegnerlukas.sqldsl2.grammar.update.UpdateStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl2.schema.OnConflict
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.tokens.CsvListToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

open class GenericUpdateGenerator(private val genCtx: GeneratorContext) : UpdateGenerator, GenericGeneratorBase<UpdateStatement>() {

	override fun buildToken(e: UpdateStatement): Token {
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

	protected fun target(e: CommonTarget): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
	}

	protected fun set(e: UpdateSetStatement): Token {
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


	protected fun from(e: FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(CsvListToken(e.expressions.map { genCtx.from().buildToken(it) }))
	}


	protected fun where(e: WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(genCtx.where().buildToken(e.expression))
	}

	protected fun returning(e: ReturningStatement): Token {
		return ListToken()
			.add("RETURNING")
			.add(
				when (e) {
					is ReturnAllColumnsStatement -> StringToken("*")
					is ReturnColumnsStatement -> CsvListToken(e.columns.map {
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