package de.ruegnerlukas.sqldsl.dsl.grammar.delete

import de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement

class DeleteStatement(
	val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget,
	val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement? = null,
	val returning: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement? = null
)

