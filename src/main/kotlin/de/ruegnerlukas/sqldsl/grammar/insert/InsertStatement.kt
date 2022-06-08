package de.ruegnerlukas.sqldsl.grammar.insert

import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.schema.OnConflict

class InsertStatement(
	val onConflict: OnConflict,
	val target: CommonTarget,
	val fields: InsertFieldsStatement,
	val content: InsertContentStatement,
	val returning: ReturningStatement? = null
)