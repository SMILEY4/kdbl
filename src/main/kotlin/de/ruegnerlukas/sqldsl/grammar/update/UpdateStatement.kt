package de.ruegnerlukas.sqldsl.grammar.update

import de.ruegnerlukas.sqldsl.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl.schema.OnConflict

class UpdateStatement(
	val onConflict: OnConflict,
	val target: CommonTarget,
	val set: UpdateSetStatement,
	val where: WhereStatement? = null,
	val from: FromStatement? = null,
	val returning: ReturningStatement? = null
)
