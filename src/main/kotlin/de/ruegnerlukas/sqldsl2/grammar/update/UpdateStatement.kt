package de.ruegnerlukas.sqldsl2.grammar.update

import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.insert.ReturningStatement
import de.ruegnerlukas.sqldsl2.grammar.target.CommonTarget
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement
import de.ruegnerlukas.sqldsl2.schema.OnConflict

class UpdateStatement(
	val onConflict: OnConflict,
	val target: CommonTarget,
	val set: UpdateSetStatement,
	val where: WhereStatement? = null,
	val from: FromStatement? = null,
	val returning: ReturningStatement? = null
)