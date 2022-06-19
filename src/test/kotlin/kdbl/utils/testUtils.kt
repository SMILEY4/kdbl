package kdbl.utils

import de.ruegnerlukas.kdbl.codegen.SQLCodeGeneratorImpl
import de.ruegnerlukas.kdbl.codegen.dialects.SQLiteDialect
import de.ruegnerlukas.kdbl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.kdbl.dsl.statements.InsertBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.kdbl.dsl.statements.UpdateBuilderEndStep
import kotlin.test.assertEquals

fun assertQuery(stmt: InsertBuilderEndStep, expected: String) {
	val strQuery = SQLCodeGeneratorImpl(SQLiteDialect()).insert(stmt.build()).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}

fun assertQuery(stmt: CreateTableStatement, expected: String) {
	val strQuery = SQLCodeGeneratorImpl(SQLiteDialect()).create(stmt).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}

fun assertQuery(query: QueryBuilderEndStep<*>, expected: String) {
	val strQuery = SQLCodeGeneratorImpl(SQLiteDialect()).query(query.build<Any>()).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}

fun assertQuery(stmt: UpdateBuilderEndStep, expected: String) {
	val strQuery = SQLCodeGeneratorImpl(SQLiteDialect()).update(stmt.build()).buildString()
	println(strQuery)
	assertEquals(expected, strQuery)
}