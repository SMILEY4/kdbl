package de.ruegnerlukas.sqldsl.grammar.delete

import de.ruegnerlukas.sqldsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement

class DeleteStatement(
	val target: CommonTarget,
	val where: WhereStatement? = null,
	val returning: ReturningStatement? = null
)

