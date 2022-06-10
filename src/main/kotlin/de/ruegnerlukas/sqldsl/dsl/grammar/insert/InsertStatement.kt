package de.ruegnerlukas.sqldsl.dsl.grammar.insert

import de.ruegnerlukas.sqldsl.dsl.grammar.column.OnConflict

class InsertStatement(
	val onConflict: OnConflict,
	val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget,
	val fields: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertFieldsStatement,
	val content: de.ruegnerlukas.sqldsl.dsl.grammar.insert.InsertContentStatement,
	val returning: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement? = null
)