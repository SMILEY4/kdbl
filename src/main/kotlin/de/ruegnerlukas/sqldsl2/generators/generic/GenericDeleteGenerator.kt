package de.ruegnerlukas.sqldsl2.generators.generic

import de.ruegnerlukas.sqldsl2.generators.DeleteGenerator
import de.ruegnerlukas.sqldsl2.generators.GeneratorContext
import de.ruegnerlukas.sqldsl2.grammar.delete.DeleteStatement
import de.ruegnerlukas.sqldsl2.grammar.expr.AliasColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnAllColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturnColumnsStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.tokens.CsvListToken
import de.ruegnerlukas.sqldsl2.tokens.ListToken
import de.ruegnerlukas.sqldsl2.tokens.StringToken
import de.ruegnerlukas.sqldsl2.tokens.Token

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
							is QualifiedColumn -> genCtx.columnExpr().buildToken(it)
							is AliasColumn -> ListToken().add(genCtx.select().buildToken(it.getContent())).add("AS").add(it.getColumnName())
							else -> throwUnknownType(it)
						}
					})
					else -> throwUnknownType(e)
				}
			)
	}


}