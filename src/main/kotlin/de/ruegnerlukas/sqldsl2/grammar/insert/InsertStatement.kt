package de.ruegnerlukas.sqldsl2.grammar.insert

import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.schema.OnConflict

class InsertStatement(
	val onConflict: OnConflict,
	val target: CommonTarget,
	val fields: InsertFieldsStatement,
	val content: InsertContentStatement,
	val returning: ReturningStatement? = null
)