package de.ruegnerlukas.sqldsl2.grammar.delete

import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

class DeleteStatement(
	val target: CommonTarget,
	val where: WhereStatement? = null,
	val returning: ReturningStatement? = null
)

