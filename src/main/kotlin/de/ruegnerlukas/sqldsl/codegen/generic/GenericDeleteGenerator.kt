package de.ruegnerlukas.sqldsl.codegen.generic

import de.ruegnerlukas.sqldsl.codegen.DeleteGenerator
import de.ruegnerlukas.sqldsl.codegen.GeneratorContext
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.dsl.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl.dsl.schema.Table
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token

open class GenericDeleteGenerator(private val genCtx: GeneratorContext) : DeleteGenerator, GenericGeneratorBase<de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement>() {

	override fun buildToken(e: de.ruegnerlukas.sqldsl.dsl.grammar.delete.DeleteStatement): Token {
		return ListToken()
			.add("DELETE FROM")
			.add(target(e.target))
			.addIf(e.where != null) { where(e.where!!) }
			.addIf(e.returning != null) { returning(e.returning!!) }
	}


	protected fun target(e: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
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


}