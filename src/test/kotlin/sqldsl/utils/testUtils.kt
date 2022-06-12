package sqldsl.utils

import de.ruegnerlukas.sqldsl.codegen.SQLCodeGenerator
import de.ruegnerlukas.sqldsl.codegen.dialects.SQLiteDialect
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateBuilderEndStep
import kotlin.test.assertEquals

fun assertQuery(stmt: InsertBuilderEndStep, expected: String) {
	val strQuery = SQLCodeGenerator(SQLiteDialect()).insert(stmt.build()).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}

fun assertQuery(stmt: CreateTableStatement, expected: String) {
	val strQuery = SQLCodeGenerator(SQLiteDialect()).create(stmt).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}

fun assertQuery(query: QueryBuilderEndStep<*>, expected: String) {
	val strQuery = SQLCodeGenerator(SQLiteDialect()).query(query.build<Any>()).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}

fun assertQuery(stmt: UpdateBuilderEndStep, expected: String) {
	val strQuery = SQLCodeGenerator(SQLiteDialect()).update(stmt.build()).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}