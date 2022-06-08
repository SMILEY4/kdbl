package de.ruegnerlukas.sqldsl.generators.generic

import de.ruegnerlukas.sqldsl.generators.DeleteGenerator
import de.ruegnerlukas.sqldsl.generators.GeneratorContext
import de.ruegnerlukas.sqldsl.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl.schema.Table
import de.ruegnerlukas.sqldsl.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.tokens.ListToken
import de.ruegnerlukas.sqldsl.tokens.StringToken
import de.ruegnerlukas.sqldsl.tokens.Token

open class GenericDeleteGenerator(private val genCtx: GeneratorContext) : DeleteGenerator, GenericGeneratorBase<DeleteStatement>() {

	override fun buildToken(e: DeleteStatement): Token {
		return ListToken()
			.add("DELETE FROM")
			.add(target(e.target))
			.addIf(e.where != null) { where(e.where!!) }
			.addIf(e.returning != null) { returning(e.returning!!) }
	}


	protected fun target(e: CommonTarget): Token {
		return when (e) {
			is Table<*> -> StringToken(e.tableName)
			else -> throwUnknownType(e)
		}
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


}